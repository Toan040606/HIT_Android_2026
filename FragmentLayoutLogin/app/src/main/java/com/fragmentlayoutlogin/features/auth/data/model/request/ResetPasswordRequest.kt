package com.fragmentlayoutlogin.features.auth.data.model.request

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("email")
    val email: String?=null,
    @SerializedName("otp")
    val otp: String?=null,
    @SerializedName("newPassword")
    val newPassword: String?=null
)
