package com.fragmentlayoutlogin.features.auth.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import com.fragmentlayoutlogin.features.auth.ui.view.forgotpassword.ForgotPasswordViewModel
import com.fragmentlayoutlogin.features.auth.ui.view.login.LoginViewModel
import com.fragmentlayoutlogin.features.auth.ui.view.resetpassword.ResetPasswordViewModel
import com.fragmentlayoutlogin.features.auth.ui.view.signup.CreateUserViewModel
import com.fragmentlayoutlogin.features.auth.ui.view.verifyemail.VerifyEmailViewModel

class AuthVMFactory(
    private val repo: AuthRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(repo) as T

            modelClass.isAssignableFrom(CreateUserViewModel::class.java) ->
                CreateUserViewModel(repo) as T

            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) ->
                ForgotPasswordViewModel(repo) as T

            modelClass.isAssignableFrom(VerifyEmailViewModel::class.java) ->
                VerifyEmailViewModel(repo) as T

            modelClass.isAssignableFrom(ResetPasswordViewModel::class.java) ->
                ResetPasswordViewModel(repo) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}