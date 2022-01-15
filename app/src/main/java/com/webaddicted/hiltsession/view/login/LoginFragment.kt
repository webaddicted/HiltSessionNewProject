package com.webaddicted.hiltsession.view.login

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.databinding.FrmLoginBinding
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import com.webaddicted.hiltsession.utils.common.GlobalUtils
import com.webaddicted.hiltsession.utils.common.GlobalUtils.showToast
import com.webaddicted.hiltsession.utils.common.ValidationHelper
import com.webaddicted.hiltsession.view.base.BaseFragment
import com.webaddicted.hiltsession.view.home.HomeActivity
import com.webaddicted.hiltsession.viewmodel.LoginViewModel

class LoginFragment : BaseFragment(R.layout.frm_login) {
    private lateinit var mBinding: FrmLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

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
        mBinding.edtEmail.setText(getString(R.string.my_mail_id))
        mBinding.edtMobile.setText(getString(R.string.my_mobie_no))
    }

    private fun clickListener() {
        mBinding.btnLogin.setOnClickListener(this)
        mBinding.txtSignup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.txt_signup -> navigateScreen(SignupFragment.TAG)
            R.id.btn_login -> {
                if (validate()) {
                    GlobalUtils.delay(1000) { _: Boolean ->
                        checkUserInfo()
                    }
                }
            }
        }
    }

    private fun checkUserInfo() {
        loginViewModel.getDbUserInfoApi(mBinding.edtEmail.text.toString())
        loginViewModel.getDbUserInfoRespo.observe(this, {
            handleApiRespo(
                it,
                mBinding.loadingTyreIv
            ) { isFailure: Boolean, result: ApiResponse<UserInfoRespo>? ->
                if (isFailure) checkUserInfo()
                else {
                    val user = result?.data
                    if (user?.email.equals(mBinding.edtEmail.text.toString()) &&
                        user?.mobilePhone.equals(mBinding.edtMobile.text.toString())
                    ) {
                        activity?.showToast(getString(R.string.login_successfully))
                        loginViewModel.setPrefUserInfo(user)
                        HomeActivity.newClearLogin(mActivity)
                    } else {
                        activity?.showToast(getString(R.string.email_mobile_not_match))
                    }
                }
            }
        })
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
            SignupFragment.TAG -> frm = SignupFragment.getInstance(Bundle())
        }
        if (frm != null) navigateFragment(R.id.container, frm, true)
    }
}