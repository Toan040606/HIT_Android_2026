package com.fragmentlayoutlogin.features.auth.data.model.request

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest (
    @SerializedName("email")
    val email: String?=null
)