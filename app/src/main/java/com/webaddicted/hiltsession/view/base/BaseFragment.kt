package com.webaddicted.hiltsession.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import com.webaddicted.hiltsession.utils.common.GlobalUtils
import com.webaddicted.hiltsession.utils.common.NetworkHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseFragment(private val layoutId: Int) : Fragment(), View.OnClickListener {
    private lateinit var mBinding: ViewDataBinding
    protected val mActivity by lazy { requireActivity() }

    @Inject
    lateinit var networkHelper: NetworkHelper
    protected abstract fun onBindTo(binding: ViewDataBinding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onBindTo(mBinding)
        super.onViewCreated(view, savedInstanceState)
        GlobalUtils.hideKeyboardFrom(mActivity, mBinding.root)
    }

    override fun onClick(v: View) {}

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
        layoutContainer: Int, fragment: Fragment, isEnableBackStack: Boolean
    ) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layoutContainer, fragment)
        if (isEnableBackStack)
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun <T> handleApiRespo(
        apiResponse: ApiResponse<T>, loadingView: View?,
        responseObserver: (isFailure: Boolean, result: ApiResponse<T>?) -> Unit
    ) {
        handleApiRespo(apiResponse, loadingView, responseObserver)
    }

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