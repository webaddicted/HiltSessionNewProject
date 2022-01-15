package com.webaddicted.hiltsession.utils.common

import android.widget.TextView
import androidx.annotation.NonNull
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.webaddicted.hiltsession.R
import java.util.regex.Matcher
import java.util.regex.Pattern

object ValidationHelper {

    fun isBlank(@NonNull targetEditText: TextView): Boolean {
        return targetEditText.text.toString().trim { it <= ' ' }.isEmpty()
    }

    fun validateBlank(
        textInput: TextInputEditText,
        wrapper: TextInputLayout,
        enter_subject: String
    ): Boolean {
        if (isBlank(textInput)) {
            wrapper.error = enter_subject
        } else {
            wrapper.error = null
            return true
        }
        return false
    }

    fun validateName(edtName: TextInputEditText, wrapperName: TextInputLayout): Boolean {
        if (isBlank(edtName)) {
            wrapperName.error = edtName.context.resources.getString(R.string.enter_first_name)
        } else {
            wrapperName.error = null
            return true
        }
        return false
    }

    fun validateEmail(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
        val pattern =
            Pattern.compile("([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})")
        val matcher: Matcher = pattern.matcher(textInput.text.toString().trim())
        when {
            isBlank(textInput) -> {
                wrapper.error = wrapper.context.resources.getString(R.string.enter_your_email)
            }
            !matcher.matches() -> {
                wrapper.error =
                    textInput.context.resources.getString(R.string.error_enter_valid_email)
            }
            else -> {
                wrapper.error = null
                return true
            }
        }
        return false
    }

    fun validateDob(edtDob: TextInputEditText, wrapperDob: TextInputLayout): Boolean {
        if (isBlank(edtDob)) {
            wrapperDob.error = edtDob.context.resources.getString(R.string.enter_dob)
        } else {
            wrapperDob.error = null
            return true
        }
        return false
    }

    fun validateMobileNo(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
        val pattern = Pattern.compile("(\\+\\d{1,3}[- ]?)?\\d{10}")
        val matcher: Matcher = pattern.matcher(textInput.text.toString().trim())
        when {
            isBlank(textInput) -> {
                wrapper.error = wrapper.context.resources.getString(R.string.enter_mobile_no)
            }
            !matcher.matches() -> {
                wrapper.error =
                    textInput.context.resources.getString(R.string.enter_valid_mobile_no)
            }
            else -> {
                wrapper.error = null
                return true
            }
        }
        return false
    }
    fun validateAddress(edtName: TextInputEditText, wrapperName: TextInputLayout): Boolean {
        if (isBlank(edtName)) {
            wrapperName.error = edtName.context.resources.getString(R.string.enter_address)
        } else {
            wrapperName.error = null
            return true
        }
        return false
    }
    fun validatePostalCode(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
        val pattern = Pattern.compile("[1-9][0-9]{5}")
        val matcher: Matcher = pattern.matcher(textInput.text.toString().trim())
        when {
            isBlank(textInput) -> {
                wrapper.error = wrapper.context.resources.getString(R.string.enter_postal_code)
            }
            !matcher.matches() -> {
                wrapper.error =
                    textInput.context.resources.getString(R.string.enter_valid_postal_code)
            }
            else -> {
                wrapper.error = null
                return true
            }
        }
        return false
    }

    fun validateVoterId(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
        // DL/01/001/000000
        // DLE2345678
        val pattern = Pattern.compile("([a-zA-Z]){3}([0-9]){7}")
        val matcher: Matcher = pattern.matcher(textInput.text.toString().trim())
        when {
            isBlank(textInput) -> {
                wrapper.error = wrapper.context.resources.getString(R.string.enter_voter_id_number)
            }
            !matcher.matches() -> {
                wrapper.error =
                    textInput.context.resources.getString(R.string.enter_valid_voter_id_number)
            }
            else -> {
                wrapper.error = null
                return true
            }
        }
        return false
    }

    fun validatePanNo(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
        val pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
        val matcher: Matcher = pattern.matcher(textInput.text.toString().trim())
        when {
            isBlank(textInput) -> {
                wrapper.error = wrapper.context.resources.getString(R.string.enter_pan_no)
            }
            !matcher.matches() -> {
                wrapper.error = textInput.context.resources.getString(R.string.enter_valid_pan_no)
            }
            else -> {
                wrapper.error = null
                return true
            }
        }
        return false
    }

    fun validateAadharNo(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
        //776688554455
        val pattern = Pattern.compile("[2-9][0-9]{11}")
        val matcher: Matcher = pattern.matcher(textInput.text.toString().trim())
        when {
            isBlank(textInput) -> {
                wrapper.error = wrapper.context.resources.getString(R.string.enter_aadhar_no)
            }
            !matcher.matches() -> {
                wrapper.error =
                    textInput.context.resources.getString(R.string.enter_valid_aadhar_no)
            }
            else -> {
                wrapper.error = null
                return true
            }
        }
        return false
    }

    fun validateDL(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
        // MH1420110062821
        val pattern = Pattern.compile("([a-zA-Z]){2}[0-9]{13}")
        val matcher: Matcher = pattern.matcher(textInput.text.toString().trim())
        when {
            isBlank(textInput) -> {
                wrapper.error =
                    wrapper.context.resources.getString(R.string.enter_driving_license_no)
            }
            !matcher.matches() -> {
                wrapper.error =
                    textInput.context.resources.getString(R.string.enter_valid_driving_license_no)
            }
            else -> {
                wrapper.error = null
                return true
            }
        }
        return false
    }

    fun validatePassport(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
        //A2096457
        val pattern = Pattern.compile("([A-Z]){1}[1-9][0-9]{5}[1-9]{1}")
        val matcher: Matcher = pattern.matcher(textInput.text.toString().trim())
        when {
            isBlank(textInput) -> {
                wrapper.error = wrapper.context.resources.getString(R.string.enter_passport_no)
            }
            !matcher.matches() -> {
                wrapper.error =
                    textInput.context.resources.getString(R.string.enter_valid_passport_no)
            }
            else -> {
                wrapper.error = null
                return true
            }
        }
        return false
    }

    fun validateGSTINNo(textInput: TextInputEditText, wrapper: TextInputLayout): Boolean {
        val pattern =
            Pattern.compile("\\d{2}[a-zA-Z]{5}\\d{4}[a-zA-Z]{1}\\d{1}[zZ]{1}[\\da-zA-Z]{1}")
        val matcher: Matcher = pattern.matcher(textInput.text.toString().trim())
//           var matcher = Pattern.matches("\\d{2}[a-zA-Z]{5}\\d{4}[a-zA-Z]{1}\\d{1}[zZ]{1}[\\da-zA-Z]{1}",textInput.text.toString().trim());
        when {
            isBlank(textInput) -> {
                wrapper.error = wrapper.context.resources.getString(R.string.enter_gstin_no)
            }
            !matcher.matches() -> {
                wrapper.error = textInput.context.resources.getString(R.string.enter_valid_gstin_no)
            }
            else -> {
                wrapper.error = null
                return true
            }
        }
        return false
    }

}