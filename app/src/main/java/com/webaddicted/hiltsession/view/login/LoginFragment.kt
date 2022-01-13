package com.webaddicted.hiltsession.view.login

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.databinding.FrmLoginBinding
import com.webaddicted.hiltsession.utils.common.GlobalUtils
import com.webaddicted.hiltsession.utils.common.ValidationHelper
import com.webaddicted.hiltsession.view.base.BaseFragment
import com.webaddicted.hiltsession.view.home.HomeActivity
import com.webaddicted.hiltsession.viewmodel.HomeViewModel

class LoginFragment : BaseFragment(R.layout.frm_login) {
    private lateinit var userInfo: UserModel
    private lateinit var mBinding: FrmLoginBinding
    val homeVM: HomeViewModel by viewModels()

    companion object {
        val TAG = LoginFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): LoginFragment {
            val fragment = LoginFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmLoginBinding
        init()
        clickListener()
    }

    private fun init() {

    }

    private fun clickListener() {
        mBinding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_login -> {
                if (validate()) {
                    Glide.with(this).load(R.raw.loader)
                        .into(DrawableImageViewTarget(mBinding.loadingTyreIv))
                    userInfo = homeVM.getPrefUserInfo()
                    GlobalUtils.delay(4000) { _: Boolean ->
                        HomeActivity.newClearLogin(mActivity)
                    }
                }
            }
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        if (!ValidationHelper.validateMobileNo(mBinding.edtMobile, mBinding.inputMobile))
            isValid = false
        if (!ValidationHelper.validateEmail(mBinding.edtEmail, mBinding.inputEmail)) isValid = false
        return isValid
    }

    private fun navigateScreen(tag: String?) {
        var frm: Fragment? = null
        when (tag) {
//                  UserTypeFragment.TAG -> frm = UserTypeFragment.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, false)
    }
}