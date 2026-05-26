package com.fragmentlayoutlogin.features.auth.ui.view.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fragmentlayoutlogin.core.network.ApiResponse
import com.fragmentlayoutlogin.core.ui.UiState
import com.fragmentlayoutlogin.core.utils.DataResult
import com.fragmentlayoutlogin.features.auth.data.model.response.ForgotPasswordResponse
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val repo: AuthRepository
) : ViewModel() {
    private val _forgotPasswordResult =
        MutableStateFlow<UiState<ApiResponse<ForgotPasswordResponse>>>(UiState.Idle)
    val forgotPasswordResult: StateFlow<UiState<ApiResponse<ForgotPasswordResponse>>> =
        _forgotPasswordResult.asStateFlow()

    fun forgotPassword(email: String) {
        if (email.isEmpty()) {
            _forgotPasswordResult.value = UiState.Error("Please enter your email")
            return
        }

        viewModelScope.launch {
            _forgotPasswordResult.value = UiState.Loading
            when (val result = repo.forgotPassword(email)) {
                is DataResult.Success -> {
                    _forgotPasswordResult.value = UiState.Success(result.data)
                }
                is DataResult.Error -> {
                    _forgotPasswordResult.value = UiState.Error(result.message)
                }
            }
        }
    }
}
