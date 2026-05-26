package com.fragmentlayoutlogin.features.auth.data.model.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: String?= null,
    @SerializedName("name")
    val username: String?= null,
    @SerializedName("email")
    val email: String?= null
)
