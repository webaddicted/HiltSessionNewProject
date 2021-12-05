package com.webaddicted.hiltsession.view.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.databinding.ActivityCommonBinding
import com.webaddicted.hiltsession.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

class SplashActivity : BaseActivity(R.layout.activity_common) {
    private lateinit var mBinding: ActivityCommonBinding


    companion object {
        val TAG = SplashActivity::class.qualifiedName
        fun newIntent(activity: Activity) {
            activity.startActivity(Intent(activity, SplashActivity::class.java))
        }

        fun newClearLogin(context: Activity?) {
            val intent = Intent(context, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
            context?.finish()
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as ActivityCommonBinding
        init()
        clickListener()
    }

    private fun init() {
//    val data = loginViewModel.getUserInfo()
//    Log.d("TAG","Test Data ${data.page}")
        navigateToNext(SplashFragment.TAG)
    }

    private fun clickListener() {

    }

    private fun navigateToNext(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            SplashFragment.TAG -> frm = SplashFragment.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
//    Handler(Looper.getMainLooper()).postDelayed({
//      LoginActivity.newClearLogin(this)
//    }, AppConstant.SPLASH_DELAY)
    }

//  fun getUserInfo(): UserModel {
//    return loginViewModel.getUserInfo()
//  }
}