package com.fragmentlayoutlogin.features.auth.ui.view.resetpassword

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

class ResetPasswordViewModel( private val repo: AuthRepository ) : ViewModel() {
    private val _resetPasswordResult : MutableStateFlow<UiState<ApiResponse<Void>>> = MutableStateFlow(UiState.Idle)
    val resetPasswordResult : StateFlow<UiState<ApiResponse<Void>>> = _resetPasswordResult.asStateFlow()

    fun resetPassword(email: String, otp: String, password: String, confirmPassword: String) {
        if (email.isEmpty() || otp.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            _resetPasswordResult.value = UiState.Error("Please fill in all fields")
            return
        }

        if (password != confirmPassword) {
            _resetPasswordResult.value = UiState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            _resetPasswordResult.value = UiState.Loading
            when (val result = repo.resetPassword(email, otp, password)) {
                is DataResult.Success -> {
                    _resetPasswordResult.value = UiState.Success(result.data)
                }
                is DataResult.Error -> {
                    _resetPasswordResult.value = UiState.Error(result.message)
                }
            }
        }
    }
}