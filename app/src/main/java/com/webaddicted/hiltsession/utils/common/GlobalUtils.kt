package com.webaddicted.hiltsession.utils.common

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.net.ParseException
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.format.DateFormat
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.google.gson.Gson
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.utils.constant.AppConstant
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.lang.reflect.Type
import java.net.URLEncoder
import java.text.NumberFormat
import java.util.*

object GlobalUtils {
    //  var restClient: RestClient? = null
    var toast: Toast? = null
    fun Context.showToast(message: String?) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast?.show()
    }

    fun deserializeObj(jsonStr: String, type: Type): Any {
        return Gson().fromJson(jsonStr, type)
    }

    fun serializeObj(obj: Any): String {
        return Gson().toJson(obj)
    }

    fun hideKeyboardFrom(context: Context, view: View?) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun Context.makePhoneCall(number: String?): Boolean {
        return try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            startActivity(intent)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun Context.onWhatsAppClick(mActivity: Activity, mobileNo: String?) {
        if (mobileNo.isNullOrEmpty()) {
            DialogUtils.showDialog(
                mActivity,
                getString(R.string.error),
                getString(R.string.contact_number_not_available)
            )
            return
        }
        val selectedMobileNo = "+91$mobileNo"
        val packageManager = packageManager
        val i = Intent(Intent.ACTION_VIEW)

        try {
            val url =
                "https://api.whatsapp.com/send?phone=$selectedMobileNo&text=" + URLEncoder.encode(
                    "",
                    "UTF-8"
                )
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
//        if (i.resolveActivity(packageManager) != null) {
            startActivity(i)
//        } else {
//          DialogUtils.showDialog(mActivity,getString(R.string.error),getString(R.string.please_ensure_you_have_whatsapp_installed))
////          showDialog(getString(R.string.please_ensure_you_have_whatsapp_installed))
//        }
        } catch (e: Exception) {
            DialogUtils.showDialog(
                mActivity,
                getString(R.string.error),
                getString(R.string.please_ensure_you_have_whatsapp_installed)
            )
//        showDialog(getString(R.string.please_ensure_you_have_whatsapp_installed))
        }
    }

    fun setBackgroundTintColor(view: TextView, color: Int) {
        view.backgroundTintList = ContextCompat.getColorStateList(view.context, color)
    }

    fun logPrint(tag: String? = "TAG", msg: String? = "") {
        msg?.let { Log.d(tag, it) }
    }

    fun getDate(context: Context, mDobEtm: TextView) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                var dateString = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
                dateString = getFormattedDateFromDate(
                    dateString,
                    AppConstant.DISPLAY_DATE_FORMAT,
                    AppConstant.SERVER_DATE_FORMAT
                )
                mDobEtm.text = dateString
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DATE)
        )
        datePickerDialog.show()
    }

    fun getFormattedDateFromDate(
        dateStr: String?,
        outputFormat: String,
        inputFormat: String
    ): String {
        if (dateStr == null) return ""
        val date: Date?
        var strDate = ""
        try {
            date = SimpleDateFormat(inputFormat, Locale.US).parse(dateStr)
            strDate = SimpleDateFormat(outputFormat, Locale.US).format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return strDate
    }

    fun Context.showDrawable(name: String?, view: ImageView?, addTint: Boolean = true) {
        val drawableName = "menu_${
            name?.lowercase(Locale.getDefault())
                ?.replace(" ", "_")
                ?.replace("-", "")
                ?.replace("&", "")
                ?.replace("/", "_")
                ?.replace("(", "")
                ?.replace(")", "")
        }"
        try {
            val drawableId =
                resources.getIdentifier(drawableName, "drawable", packageName)
            val drawable = ContextCompat.getDrawable(this, drawableId)
            if (addTint)
                drawable?.setTint(ContextCompat.getColor(this, R.color.appBlue))
            view?.setImageDrawable(drawable)
        } catch (e: Exception) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.logo)
            view?.setImageDrawable(drawable)
        }
    }

    fun Context.showCircleDrawable(name: String?, view: ImageView?) {
        val drawableName = "menu_${
            name?.lowercase(Locale.getDefault())
                ?.replace(" ", "_")
                ?.replace("-", "")
                ?.replace("&", "")
                ?.replace("(", "")
                ?.replace(")", "")
        }"
        try {
            val drawableId =
                resources.getIdentifier(drawableName, "drawable", packageName)
            val drawable = ContextCompat.getDrawable(this, drawableId)
            Glide.with(this)
                .load(drawableId)
                .circleCrop()
                .into(view!!)
//        view?.setImageDrawable(drawable)
        } catch (e: Exception) {
            val drawable = ContextCompat.getDrawable(this, R.drawable.logo)
            view?.setImageDrawable(drawable)
        }
    }

    fun getCurrencyFormat(context: Context, number: String?): String? {
        if (number.isNullOrEmpty()) return null

        val twoDecimalNo =
            String.format(context.getString(R.string.dbl_format_2_pts), number.toDouble())
        //val twoDecimalNo = decimalFormat(number.toDouble())
        return NumberFormat.getCurrencyInstance(Locale("en", "IN"))
            .format(twoDecimalNo.toDouble())
    }

    fun uriToBitmap(mContext: Context, uri: Uri): Bitmap {
        return BitmapFactory.decodeStream(mContext.contentResolver.openInputStream(uri))
    }

    fun delay(secDelay: Int = 1, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            function()
        }, secDelay * 1000L)
    }

    fun getCurrentDate(): String {
        val delegate = AppConstant.DISPLAY_DATE_FORMAT
        return DateFormat.format(delegate, Calendar.getInstance().time).toString()
    }

    fun getCurrentTime(): String {
        val delegate = AppConstant.TIME_AM_PM_FORMAT
        return DateFormat.format(delegate, Calendar.getInstance().time).toString()
    }

    fun refreshAuthToken(mContext: Context): String {
        val newAuthToken: String = ""
        val t = object : Thread() {
            override fun run() {
                //println("http : refreshAuthToken")
//        val sfRepo = restClient.getInstance(mContext)
//                    SalesforceClient.mClient?.let { client ->
//                      newAuthToken = ClientManager.AccMgrAuthTokenProvider(
//                        SalesforceSDKManager.getInstance().clientManager,
//                        client.clientInfo.instanceUrlAsString,
//                        client.authToken,
//                        client.refreshToken
//                      ).newAuthToken
//                      //println("http : http newAuthToken : $newAuthToken")
//                    }
            }
        }
        t.start()
        t.join()
        return newAuthToken ?: ""
    }

    fun versionCompare(v1: String?, v2: String?): Boolean {
        val currVersion = v2?.split('.')
        val minPrefVersion = v1?.split('.')

        var i = 0
        when {
            currVersion!![i].toInt() < minPrefVersion!![i].toInt() -> {
                return false
            }
            currVersion[i].toInt() > minPrefVersion[i].toInt() -> {
                return true
            }
            else -> {
                i++
                when {
                    currVersion[i].toInt() > minPrefVersion[i].toInt() -> {
                        return true
                    }
                    currVersion[i].toInt() < minPrefVersion[i].toInt() -> {
                        return false
                    }
                    else -> {
                        i++
                        return when {
                            currVersion[i].toInt() > minPrefVersion[i].toInt() -> {
                                true
                            }
                            currVersion[i].toInt() < minPrefVersion[i].toInt() -> {
                                false
                            }
                            currVersion[i].toInt() == minPrefVersion[i].toInt() -> {
                                true
                            }
                            else -> {
                                false
                            }
                        }
                    }
                }
            }
        }

    }

    fun getDiffInDays(date1: Long, date2: Long?): Int {
        val diff = date1 - date2!!
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        return days.toInt()
    }

    fun setUserInteraction(isEnable: Boolean, window: Window?) {
        if (isEnable) {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    fun delay(duration: Long, clickListener: (Boolean) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            clickListener(true)
        }, duration)
    }

    fun showImage(imgView: ImageView, url: String, isCircleImg: Boolean = false, radius: Int = 20) {
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        if (isCircleImg)
            options.transform(CenterCrop(), RoundedCorners(radius))
        Glide.with(imgView.context).load(url)
            .apply(options)
            .into(imgView)
    }
}