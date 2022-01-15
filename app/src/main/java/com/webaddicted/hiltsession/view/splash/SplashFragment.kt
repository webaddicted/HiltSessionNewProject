package com.webaddicted.hiltsession.view.splash

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.databinding.FrmSplashBinding
import com.webaddicted.hiltsession.utils.common.GlobalUtils
import com.webaddicted.hiltsession.view.base.BaseFragment
import com.webaddicted.hiltsession.view.home.HomeActivity
import com.webaddicted.hiltsession.view.login.LoginFragment
import com.webaddicted.hiltsession.viewmodel.LoginViewModel

class SplashFragment : BaseFragment(R.layout.frm_splash) {
    private var userInfo: UserInfoRespo? = null
    private lateinit var mBinding: FrmSplashBinding
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        val TAG = SplashFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): SplashFragment {
            val fragment = SplashFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmSplashBinding
        init()
    }

    private fun init() {
        Glide.with(this).load(R.raw.loader).into(DrawableImageViewTarget(mBinding.loadingTyreIv))
        userInfo = loginViewModel.getPrefUserInfo()
        GlobalUtils.delay(4000) { isLoading: Boolean ->
            if (userInfo?.email != null && userInfo?.email?.isNotEmpty()!!)
                HomeActivity.newClearLogin(mActivity)
            else navigateScreen(LoginFragment.TAG)
        }
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            LoginFragment.TAG -> frm = LoginFragment.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}