package com.fragmentlayoutlogin.features.auth.ui.view.verifyemail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fragmentlayoutlogin.core.network.ApiResponse
import com.fragmentlayoutlogin.core.ui.UiState
import com.fragmentlayoutlogin.core.utils.DataResult
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerifyEmailViewModel(
    private val repo: AuthRepository
) : ViewModel() {
    private val _verifyEmailResult = MutableStateFlow<UiState<ApiResponse<Void>>>(UiState.Idle)
    val verifyEmailResult : StateFlow<UiState<ApiResponse<Void>>> = _verifyEmailResult.asStateFlow()

    fun verifyEmail(email: String, otp: String, mainOtp:String) {
        if (email.isEmpty() || otp.isEmpty()) {
            _verifyEmailResult.value = UiState.Error("Please fill in all fields")
            return
        }

        if (otp != mainOtp) {
            _verifyEmailResult.value = UiState.Error("Otp is incorrect")
            return
        }

        viewModelScope.launch {
            _verifyEmailResult.value = UiState.Loading
            when (val result = repo.verifyOTP(email, otp)) {
                is DataResult.Success -> {
                    _verifyEmailResult.value = UiState.Success(result.data)
                }
                is DataResult.Error -> {
                    _verifyEmailResult.value = UiState.Error(result.message)
                }
            }
        }
    }
}