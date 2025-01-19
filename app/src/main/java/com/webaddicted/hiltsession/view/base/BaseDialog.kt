package com.webaddicted.hiltsession.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseDialog(private val layoutId: Int) : DialogFragment(), View.OnClickListener {
    private lateinit var mBinding: ViewDataBinding
    protected val mActivity by lazy { requireActivity() }
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
    }

    override fun onClick(v: View) {
    }

    //    override fun onActivityCreated(arg0: Bundle?) {
//        super.onActivityCreated(arg0)
////        dialog?.window!!
////            .attributes.windowAnimations = R.style.DialogFadeAnimation
//    }
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
}