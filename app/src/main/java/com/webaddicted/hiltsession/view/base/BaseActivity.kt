package com.webaddicted.hiltsession.view.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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
import com.webaddicted.hiltsession.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity(private val layoutId: Int) : AppCompatActivity(), View.OnClickListener {
    protected var isAllowExitApp: Boolean = false

    companion object {
        val TAG = BaseActivity::class.qualifiedName
    }

    abstract fun onBindTo(binding: ViewDataBinding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding?
        if (layoutId != 0) {
            try {
                binding = DataBindingUtil.setContentView(this, layoutId)
                onBindTo(binding)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onClick(v: View) {}

    fun navigateFragment(
        layoutContainer: Int,
        fragment: Fragment,
        isEnableBackStack: Boolean = false
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layoutContainer, fragment)
        if (parent is HomeActivity) isAllowExitApp = false

        if (isEnableBackStack)
            fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        fragmentTransaction.commitAllowingStateLoss()

    }

    fun navigateAddFragment(
        layoutContainer: Int,
        fragment: Fragment,
        isEnableBackStack: Boolean = true
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
//    fragmentTransaction.setCustomAnimations(R.anim.trans_left_in, R.anim.trans_left_out, R.anim.trans_right_in, R.anim.trans_right_out)
//    fragmentTransaction.setCustomAnimations(
//      R.animator.fragment_slide_left_enter,
//      R.animator.fragment_slide_left_exit,
//      R.animator.fragment_slide_right_enter,
//      R.animator.fragment_slide_right_exit
//    )
        fragmentTransaction.add(layoutContainer, fragment)
        if (isEnableBackStack)
            fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
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
                    this,
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
                } else loadingView?.visible()
            }
            ApiResponse.Status.ERROR -> {
                val errorMsg: String = if (BuildConfig.DEBUG) {
                    apiResponse.errorMessage.toString()
                } else {
                    getString(R.string.something_went_wrong)
                }
                DialogUtils.getDialogInstance(
                    this,
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
                            this,
                            getString(R.string.error), it,
                            getString(R.string.retry),
                            getString(R.string.cancel),
                            { dialog, which -> responseObserver(true, apiResponse) },
                            { dialog, which -> dialog.dismiss() })
                    }
                }
                loadingView?.gone()
//        responseObserver(false, apiResponse)
//        loadingView?.gone()
            }
        }
    }


    fun <T> LiveData<ApiResponse<T>>.observeOnce(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<ApiResponse<T>>
    ) {
        observe(lifecycleOwner, object : Observer<ApiResponse<T>> {
            override fun onChanged(t: ApiResponse<T>) {
                observer.onChanged(t)
                if (t?.status != ApiResponse.Status.LOADING)
                    removeObserver(this)
            }
        })
    }

    fun <T> LiveData<ApiResponse<T>>.observeOnce(observer: Observer<ApiResponse<T>>) {
        observeForever(object : Observer<ApiResponse<T>> {
            override fun onChanged(t: ApiResponse<T>) {
                observer.onChanged(t)
                if (t?.status != ApiResponse.Status.LOADING)
                    removeObserver(this)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePickerHelper.ImgPickerType.CHOOSER_CAMERA_GALLERY.value || requestCode == ImagePickerHelper.ImgPickerType.CROP_IMAGE.value)
            ImagePickerHelper.onActivityResult(this, requestCode, resultCode, data)
    }

}