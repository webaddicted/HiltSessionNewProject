package com.webaddicted.hiltsession.view.home

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.databinding.ActivityCommonBinding
import com.webaddicted.hiltsession.utils.common.GlobalUtils.showToast
import com.webaddicted.hiltsession.view.base.BaseActivity


class HomeActivity : BaseActivity(R.layout.activity_common) {
    private lateinit var mBinding: ActivityCommonBinding


    companion object {
        val TAG = HomeActivity::class.qualifiedName
        fun newIntent(activity: Activity) {
            activity.startActivity(Intent(activity, HomeActivity::class.java))
        }

        fun newClearLogin(context: Activity?) {
            val intent = Intent(context, HomeActivity::class.java)
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
        navigateToNext(HomeFragment.TAG)
    }

    private fun clickListener() {

    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentById(R.id.container) != null -> {
                when {
                    supportFragmentManager.findFragmentById(R.id.container)?.childFragmentManager?.backStackEntryCount ?: 0 > 1 -> {
                        supportFragmentManager.findFragmentById(R.id.container)?.childFragmentManager?.popBackStack()
                    }
                    supportFragmentManager.backStackEntryCount > 0 -> {
                        supportFragmentManager.popBackStack()
                    }
                    else -> {
                        if (getNoOfStackActivities() > 1 || isAllowExitApp) {
                            super.onBackPressed()
                        } else {
                            showToast(getString(R.string.press_back_again_to_exit))
                            isAllowExitApp = true
                        }
                    }
                }
            }
        }
    }

    private fun getNoOfStackActivities(): Int {
        val mngr = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mngr.appTasks[0].taskInfo.numActivities
        } else {
            mngr.getRunningTasks(10)[0].numActivities
        }
    }

    private fun navigateToNext(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
            HomeFragment.TAG -> frm = HomeFragment.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}