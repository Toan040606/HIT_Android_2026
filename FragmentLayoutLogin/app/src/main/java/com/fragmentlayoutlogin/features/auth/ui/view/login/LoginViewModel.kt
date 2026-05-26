package com.fragmentlayoutlogin.features.auth.ui.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fragmentlayoutlogin.core.network.ApiResponse
import com.fragmentlayoutlogin.core.ui.UiState
import com.fragmentlayoutlogin.core.utils.DataResult
import com.fragmentlayoutlogin.features.auth.data.model.response.LoginResponse
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val repo: AuthRepository
) : ViewModel() {
    private val _loginResult =
        MutableStateFlow<UiState<ApiResponse<LoginResponse>>>(UiState.Idle)
    val loginResult: StateFlow<UiState<ApiResponse<LoginResponse>>> =
        _loginResult.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginResult.value = UiState.Error("Please fill in all fields")
            return
        }

        viewModelScope.launch {
            _loginResult.value = UiState.Loading
            when (val result = repo.login(email, password)) {
                is DataResult.Success -> {
                    _loginResult.value = UiState.Success(result.data)
                }

                is DataResult.Error -> {
                    _loginResult.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun clearLoginResult() {
        _loginResult.value = UiState.Idle
    }
}