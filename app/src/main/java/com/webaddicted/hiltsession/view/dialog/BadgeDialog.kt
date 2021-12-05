package com.webaddicted.hiltsession.view.dialog

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.databinding.DialogBadgeBinding
import com.webaddicted.hiltsession.utils.common.DialogUtils
import com.webaddicted.hiltsession.utils.common.GlobalUtils.showDrawable
import com.webaddicted.hiltsession.view.base.BaseDialog

class BadgeDialog : BaseDialog(R.layout.dialog_badge) {
    private var badgeName: String? = ""
    private lateinit var mBinding: DialogBadgeBinding

    companion object {
        val TAG = BadgeDialog::class.qualifiedName
        val BADGE_NAME = "badge_name"

        fun dialog(badgeName: String): BadgeDialog {
            val dialog = BadgeDialog()
            val bundle = Bundle()
            bundle.putString(BADGE_NAME, badgeName)
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as DialogBadgeBinding
        badgeName = arguments?.getString(BADGE_NAME)
        init()
        clickListener()
    }

    private fun init() {
        mBinding.txtTitle.text = badgeName?.capitalize()
        mActivity.showDrawable(badgeName, mBinding.imgBadge, false)
    }

    private fun clickListener() {
        mBinding.imgClose.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        DialogUtils.fullScreenTransDialogBounds(mActivity, dialog)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.img_close -> dismiss()
        }
    }
}

