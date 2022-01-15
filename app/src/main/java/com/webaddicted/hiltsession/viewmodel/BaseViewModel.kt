package com.webaddicted.hiltsession.viewmodel

import androidx.lifecycle.ViewModel
import com.webaddicted.hiltsession.utils.common.NetworkHelper
import javax.inject.Inject

open class BaseViewModel : ViewModel() {
    @Inject lateinit var  networkHelper: NetworkHelper

}