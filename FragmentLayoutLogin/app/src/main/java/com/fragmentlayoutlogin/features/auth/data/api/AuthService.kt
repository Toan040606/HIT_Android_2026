package com.fragmentlayoutlogin.features.auth.data.api

import com.fragmentlayoutlogin.core.network.ApiConstants
import com.fragmentlayoutlogin.core.network.ApiResponse
import com.fragmentlayoutlogin.features.auth.data.model.request.CreateUserRequest
import com.fragmentlayoutlogin.features.auth.data.model.request.ForgotPasswordRequest
import com.fragmentlayoutlogin.features.auth.data.model.request.LoginRequest
import com.fragmentlayoutlogin.features.auth.data.model.request.ResetPasswordRequest
import com.fragmentlayoutlogin.features.auth.data.model.request.VerifyOtpRequest
import com.fragmentlayoutlogin.features.auth.data.model.response.CreateUserResponse
import com.fragmentlayoutlogin.features.auth.data.model.response.ForgotPasswordResponse
import com.fragmentlayoutlogin.features.auth.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(ApiConstants.AUTH_SIGNUP)
    suspend fun register(@Body request: CreateUserRequest) : Response<ApiResponse<CreateUserResponse>>
    @POST(ApiConstants.AUTH_LOGIN)
    suspend fun login(@Body request: LoginRequest) : Response<ApiResponse<LoginResponse>>
    @POST(ApiConstants.AUTH_FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ApiResponse<ForgotPasswordResponse>>
    @POST(ApiConstants.AUTH_VERIFY_OTP)
    suspend fun verifyOTP(@Body request: VerifyOtpRequest): Response<ApiResponse<Void>>
    @POST(ApiConstants.AUTH_RESET_PASSWORD)
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ApiResponse<Void>>
}