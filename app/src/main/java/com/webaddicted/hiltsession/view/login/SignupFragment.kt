package com.webaddicted.hiltsession.view.login

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.data.model.home.UserInfoRespo
import com.webaddicted.hiltsession.databinding.FrmSignupBinding
import com.webaddicted.hiltsession.utils.apiutils.ApiResponse
import com.webaddicted.hiltsession.utils.common.GlobalUtils
import com.webaddicted.hiltsession.utils.common.GlobalUtils.showToast
import com.webaddicted.hiltsession.utils.common.ValidationHelper
import com.webaddicted.hiltsession.view.base.BaseFragment
import com.webaddicted.hiltsession.viewmodel.LoginViewModel

class SignupFragment : BaseFragment(R.layout.frm_signup) {
    private lateinit var mBinding: FrmSignupBinding
    val loginViewModel: LoginViewModel by viewModels()

    companion object {
        val TAG = SignupFragment::class.qualifiedName
        fun getInstance(bundle: Bundle): SignupFragment {
            val fragment = SignupFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as FrmSignupBinding
        init()
        clickListener()
    }

    private fun init() {
        mBinding.edtFullName.setText(getString(R.string.depak_sharma))
        mBinding.edtEmail.setText(getString(R.string.my_mail_id))
        mBinding.edtMobile.setText(getString(R.string.my_mobie_no))
        mBinding.edtAddress.setText(getString(R.string.my_address))
    }

    private fun clickListener() {
        mBinding.btnSignup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.btn_signup -> {
                if (validate()) {
                    GlobalUtils.delay(1000) { _: Boolean ->
                        getUserInfoFromDb()
                    }
                }
            }
        }
    }

    private fun getUserInfoFromDb() {
        loginViewModel.setDbUserInfoApi(
            UserInfoRespo(
                mBinding.edtFullName.text.toString(),
                mBinding.edtEmail.text.toString(),
                mBinding.edtMobile.text.toString(),
                mBinding.edtAddress.text.toString()
            )
        )
        loginViewModel.setDbUserInfoRespo.observe(this, {
            handleApiRespo(
                it,
                mBinding.loadingTyreIv
            ) { isFailure: Boolean, result: ApiResponse<String>? ->
                if (isFailure) getUserInfoFromDb()
                else {
                    activity?.showToast(getString(R.string.user_info_added_successfully))
                    activity?.onBackPressed()
                }
            }
        })
    }

    private fun validate(): Boolean {
        var isValid = true
        if (!ValidationHelper.validateName(mBinding.edtFullName, mBinding.inputFullName)) isValid =
            false
        if (!ValidationHelper.validateEmail(mBinding.edtEmail, mBinding.inputEmail)) isValid = false
        if (!ValidationHelper.validateMobileNo(mBinding.edtMobile, mBinding.inputMobile))
            isValid = false
        if (!ValidationHelper.validateAddress(mBinding.edtAddress, mBinding.inputAddress)) isValid =
            false
        return isValid
    }
}