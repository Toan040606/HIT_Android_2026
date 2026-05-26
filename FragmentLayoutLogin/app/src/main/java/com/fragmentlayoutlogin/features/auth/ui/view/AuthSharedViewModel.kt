package com.fragmentlayoutlogin.features.auth.ui.view

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthSharedViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email : StateFlow<String> = _email.asStateFlow()

    private val _otp = MutableStateFlow("")
    val otp : StateFlow<String> = _otp.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setOtp(otp: String) {
        _otp.value = otp
    }

    fun clear() {
        _email.value = ""
        _otp.value = ""
    }
}