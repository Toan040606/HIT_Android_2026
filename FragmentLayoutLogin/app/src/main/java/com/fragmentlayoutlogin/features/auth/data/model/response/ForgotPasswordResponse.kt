package com.fragmentlayoutlogin.features.auth.data.model.response

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse (
    @SerializedName("otp")
    val otp: String?=null
)