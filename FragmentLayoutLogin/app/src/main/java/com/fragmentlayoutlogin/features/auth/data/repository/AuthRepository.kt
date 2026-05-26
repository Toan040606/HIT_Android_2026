package com.fragmentlayoutlogin.features.auth.data.repository

import android.provider.ContactsContract
import com.fragmentlayoutlogin.core.network.ApiResponse
import com.fragmentlayoutlogin.core.utils.DataResult
import com.fragmentlayoutlogin.features.auth.data.api.AuthService
import com.fragmentlayoutlogin.features.auth.data.model.request.CreateUserRequest
import com.fragmentlayoutlogin.features.auth.data.model.request.ForgotPasswordRequest
import com.fragmentlayoutlogin.features.auth.data.model.request.LoginRequest
import com.fragmentlayoutlogin.features.auth.data.model.request.ResetPasswordRequest
import com.fragmentlayoutlogin.features.auth.data.model.request.VerifyOtpRequest
import com.fragmentlayoutlogin.features.auth.data.model.response.CreateUserResponse
import com.fragmentlayoutlogin.features.auth.data.model.response.ForgotPasswordResponse
import com.fragmentlayoutlogin.features.auth.data.model.response.LoginResponse

class AuthRepository(
    private val authService: AuthService
) {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): DataResult<ApiResponse<CreateUserResponse>> {
        return try {
            val response = authService.register(CreateUserRequest(username, email, password))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    DataResult.Success(body)
                } else {
                    DataResult.Error(body?.message ?: "Response body is null")
                }
            } else {
                DataResult.Error("HTTP Error: ${response.code()}")
            }
        } catch (e: Exception) {
            DataResult.Error("Exception: ${e.message}")
        }
    }

    suspend fun login(email: String, password: String): DataResult<ApiResponse<LoginResponse>> {
        return try {
            val response = authService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    DataResult.Success(body)
                } else {
                    DataResult.Error(body?.message ?: "Response body is null")
                }
            } else {
                DataResult.Error("HTTP Error: ${response.code()}")
            }
        }  catch (e: Exception) {
            DataResult.Error("Exception: ${e.message}")
        }
    }

    suspend fun forgotPassword(email: String): DataResult<ApiResponse<ForgotPasswordResponse>> {
        return try {
            val response = authService.forgotPassword(ForgotPasswordRequest(email))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    DataResult.Success(body)
                } else {
                    DataResult.Error(body?.message ?: "Response body is null")
                }
            } else {
                DataResult.Error("HTTP Error: ${response.code()}")
                }
        } catch (e: Exception) {
            DataResult.Error("Exception: ${e.message}")
        }
    }

    suspend fun verifyOTP(email: String, otp: String): DataResult<ApiResponse<Void>> {
        return try {
            val response = authService.verifyOTP(VerifyOtpRequest(email, otp))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    DataResult.Success(body)
                } else {
                    DataResult.Error(body?.message ?: "Response body is null")
                }
            } else {
                DataResult.Error("HTTP Error: ${response.code()}")
            }
        } catch (e: Exception) {
            DataResult.Error("Exception: ${e.message}")
        }
    }

    suspend fun resetPassword(email: String, otp: String, password: String): DataResult<ApiResponse<Void>> {
        return try {
            val response = authService.resetPassword(ResetPasswordRequest(email, otp, password))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    DataResult.Success(body)
                } else {
                    DataResult.Error(body?.message ?: "Response body is null")
                }
            } else {
                DataResult.Error("HTTP Error: ${response.code()}")
            }
        } catch (e: Exception) {
            DataResult.Error("Exception: ${e.message}")
        }
    }
}
