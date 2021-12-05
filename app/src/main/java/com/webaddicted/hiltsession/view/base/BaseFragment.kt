package com.webaddicted.hiltsession.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.webaddicted.hiltsession.BuildConfig
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.data.model.common.CommonListRespo
import com.webaddicted.hiltsession.data.model.common.CommonRespo
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import com.webaddicted.hiltsession.utils.common.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment(private val layoutId: Int) : Fragment(), View.OnClickListener {
    //    PermissionHelper.Companion.PermissionListener {
    private lateinit var mBinding: ViewDataBinding

    protected val mActivity by lazy { requireActivity() }

    @Inject
    lateinit var networkHelper: NetworkHelper
    protected abstract fun onBindTo(binding: ViewDataBinding)
    //  private var loaderDialog: LoaderDialog? = null
//  protected val preferenceMgr: PreferenceMgr by inject()

    //  abstract fun getLayout(): Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//    mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onBindTo(mBinding)
        super.onViewCreated(view, savedInstanceState)
        GlobalUtils.hideKeyboardFrom(mActivity, mBinding.root)
//    if (loaderDialog == null) {
//      loaderDialog = LoaderDialog.dialog(false)
//    }
    }

    override fun onClick(v: View) {

    }

//  protected fun showApiLoader() {
//    if (loaderDialog != null) {
//      val fragment = fragmentManager?.findFragmentByTag(LoaderDialog.TAG)
//      if (fragment != null) fragmentManager?.beginTransaction()?.remove(fragment)?.commit()
//      loaderDialog?.show(fragmentManager!!, LoaderDialog.TAG)
//    }
//  }
//
//
//  protected fun hideApiLoader() {
//    if (loaderDialog != null && loaderDialog?.isVisible!!) loaderDialog?.dismiss()
//  }

//  protected fun <T> apiResponseHandler(view: View, response: ApiResponse<T>) {
//    when (response.status) {
//      ApiResponse.Status.LOADING -> {
//        showApiLoader()
//      }
//      ApiResponse.Status.ERROR -> {
//        hideApiLoader()
//        if (response.errorMessage != null && response.errorMessage?.length!! > 0)
//          ValidationHelper.showSnackBar(view, response.errorMessage!!)
//        else activity?.showToast(getString(R.string.something_went_wrong))
//      }
//      else -> {
//        showApiLoader()
//      }
//    }
//  }
//
//  override fun onResume() {
//    super.onResume()
//    activity?.let { GlobalUtility.hideKeyboard(it) }
//  }

    protected fun navigateFragment(
        layoutContainer: Int,
        fragment: Fragment,
        isEnableBackStack: Boolean
    ) {
        if (activity != null) {
            (activity as BaseActivity).navigateFragment(
                layoutContainer,
                fragment,
                isEnableBackStack
            )
        }
    }

    protected fun navigateAddFragment(
        layoutContainer: Int,
        fragment: Fragment,
        isEnableBackStack: Boolean
    ) {
        if (activity != null) {
            (activity as BaseActivity).navigateAddFragment(
                layoutContainer,
                fragment,
                isEnableBackStack
            )
        }
    }

    protected fun navigateChildFragment(
        layoutContainer: Int,
        fragment: Fragment,
        isEnableBackStack: Boolean
    ) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layoutContainer, fragment)
        if (isEnableBackStack)
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun <T> handleApiRespo(
        apiResponse: ApiResponse<T>,
        loadingView: View?,
        responseObserver: (isFailure: Boolean, result: ApiResponse<T>?) -> Unit
    ) {
        when (apiResponse.status) {
            ApiResponse.Status.NO_INTERNET_CONNECTION -> {
                DialogUtils.getDialogInstance(
                    mActivity,
                    getString(R.string.error),
                    getString(R.string.dialog_no_internet_msg),
                    getString(R.string.retry),
                    getString(R.string.cancel),
                    { dialog, which -> responseObserver(true, apiResponse) },
                    { dialog, which -> dialog.dismiss() })
            }
            ApiResponse.Status.LOADING -> {
                if (loadingView != null && loadingView is ImageView) {
                    loadingView.visible()
                    Glide.with(this)
                        .load(R.raw.loader)
                        .into(DrawableImageViewTarget(loadingView))
                }
            }
            ApiResponse.Status.ERROR -> {
                val errorMsg: String = if (BuildConfig.DEBUG) {
                    apiResponse.errorMessage.toString()
                } else {
                    getString(R.string.something_went_wrong)
                }
                DialogUtils.getDialogInstance(
                    mActivity,
                    getString(R.string.error), errorMsg,
                    getString(R.string.retry),
                    getString(R.string.cancel),
                    { dialog, which -> responseObserver(true, apiResponse) },
                    { dialog, which -> dialog.dismiss() })
                loadingView?.gone()
            }
            ApiResponse.Status.SUCCESS -> {
                var isSuccess: Boolean? = true
                var errorMsg: String? = ""
                if (apiResponse.data is CommonRespo<*>) {
                    isSuccess = apiResponse.data.isSuccess
                    errorMsg = apiResponse.data.strMessage
                } else if (apiResponse.data is CommonListRespo<*>) {
                    isSuccess = apiResponse.data.isSuccess
                    errorMsg = apiResponse.data.strMessage
                }
                if (isSuccess == true) {
                    responseObserver(false, apiResponse)
                } else {
                    errorMsg?.let {
                        DialogUtils.getDialogInstance(
                            mActivity,
                            getString(R.string.error), it,
                            getString(R.string.retry),
                            getString(R.string.cancel),
                            { dialog, which -> responseObserver(true, apiResponse) },
                            { dialog, which -> dialog.dismiss() })
                    }
                }
                loadingView?.gone()
            }
        }
    }


//  protected fun <T> handleApi(
//    apiResponse: ApiResponse<T>,
//    loadingView: View?,
//    errorView: View? = null,
//    showDialog: Boolean = false
//  ) {
//    errorView?.gone()
//    (activity as BaseActivity).handleApi(
//      apiResponse,
//      loadingView,
//      errorView,
//      showDialog
//    )
//  }

    fun <T> LiveData<ApiResponse<T>>.observeOnce(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<ApiResponse<T>>
    ) {
        observe(lifecycleOwner, object : Observer<ApiResponse<T>> {
            override fun onChanged(t: ApiResponse<T>?) {
                if (t?.status != ApiResponse.Status.LOADING)
                    removeObserver(this)
                observer.onChanged(t)

            }
        })
    }

    fun <T> LiveData<ApiResponse<T>>.observeOnce(observer: Observer<ApiResponse<T>>) {
        observeForever(object : Observer<ApiResponse<T>> {
            override fun onChanged(t: ApiResponse<T>?) {
                observer.onChanged(t)
                if (t?.status != ApiResponse.Status.LOADING)
                    removeObserver(this)
            }
        })
    }

//  override fun onClick(v: View) {
//    activity?.let { GlobalUtility.hideKeyboard(it) }
//    GlobalUtility.avoidDoubleClicks(v)
////    GlobalUtility.Companion.btnClickAnimation(v)
//  }

//  fun checkStoragePermission() {
//    val multiplePermission = ArrayList<String>()
//    multiplePermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    multiplePermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
//    multiplePermission.add(Manifest.permission.CAMERA)
//    if (PermissionHelper.checkMultiplePermission(activity!!, multiplePermission)) {
////      FileHelper.createApplicationFolder()
//      onPermissionGranted(multiplePermission)
//    } else
//      PermissionHelper.requestMultiplePermission(activity!!, multiplePermission, this)
//  }
//
//
//  fun checkBlinkPermission() {
//    val multiplePermission = ArrayList<String>()
//    multiplePermission.add(Manifest.permission.CAMERA)
//    multiplePermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    multiplePermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
//    multiplePermission.add(Manifest.permission.ACCESS_FINE_LOCATION)
//    multiplePermission.add(Manifest.permission.ACCESS_COARSE_LOCATION)
//    if (PermissionHelper.checkMultiplePermission(
//            activity!!,
//            multiplePermission
//        )
//    ) onPermissionGranted(multiplePermission)
//    else PermissionHelper.requestMultiplePermission(activity!!, multiplePermission, this)
//  }
//
//  override fun onPermissionGranted(mCustomPermission: List<String>) {
////    FileHelper.createApplicationFolder()
//  }
//
//
//  override fun onPermissionDenied(mCustomPermission: List<String>) {
//  }
//
//
//  fun getPlaceHolder(imageLoaderPos: Int): String {
//    val imageLoader = resources.getStringArray(R.array.image_loader)
//    return imageLoader[imageLoaderPos]
//  }
//
//  protected fun addBlankSpace(bottomSpace: LinearLayout) {
//    KeyboardEventListener(activity as AppCompatActivity) { isKeyboardOpen: Boolean, softkeybordHeight: Int ->
//      if (isKeyboardOpen)
//        bottomSpace.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            softkeybordHeight
//        )
//      else bottomSpace.layoutParams =
//          LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
//    }
//  }

}