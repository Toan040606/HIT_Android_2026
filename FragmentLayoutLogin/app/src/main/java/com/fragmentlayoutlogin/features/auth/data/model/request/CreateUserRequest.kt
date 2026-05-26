package com.fragmentlayoutlogin.features.auth.data.model.request

import com.google.gson.annotations.SerializedName

data class CreateUserRequest (
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null
)