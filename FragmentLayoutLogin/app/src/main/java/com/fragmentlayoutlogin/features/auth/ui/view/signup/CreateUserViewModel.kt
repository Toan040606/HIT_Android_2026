package com.fragmentlayoutlogin.features.auth.ui.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fragmentlayoutlogin.core.network.ApiResponse
import com.fragmentlayoutlogin.core.ui.UiState
import com.fragmentlayoutlogin.core.utils.DataResult
import com.fragmentlayoutlogin.features.auth.data.model.response.CreateUserResponse
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateUserViewModel (
    private val repo: AuthRepository
) : ViewModel() {
    private val _registerResult =
        MutableStateFlow<UiState<ApiResponse<CreateUserResponse>>>(UiState.Idle)
    val registerResult: StateFlow<UiState<ApiResponse<CreateUserResponse>>> =
        _registerResult.asStateFlow()

    fun register(username: String, email: String, password: String, confirmPassword: String) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            _registerResult.value = UiState.Error("Please fill in all fields")
            return
        }

        if (password != confirmPassword) {
            _registerResult.value = UiState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            _registerResult.value = UiState.Loading
            when (val result = repo.register(username, email, password)) {
                is DataResult.Success -> {
                    _registerResult.value = UiState.Success(result.data)
                }

                is DataResult.Error -> {
                    _registerResult.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun clearLoginResult() {
        _registerResult.value = UiState.Idle
    }
}