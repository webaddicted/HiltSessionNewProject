package com.webaddicted.hiltsession.utils.common


import android.content.Context
import android.view.View
import android.widget.Toast
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.utils.common.GlobalUtils.showToast

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
fun Context.showToastShort(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
fun Context.showNoNetworkToast() {
    showToast(resources.getString(R.string.no_network_msg))
}

fun Context.showSomethingWrongToast() {
    showToast(resources.getString(R.string.something_went_wrong))
}