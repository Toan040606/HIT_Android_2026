package com.fragmentlayoutlogin.features.auth.data.model.response

import com.fragmentlayoutlogin.features.auth.data.model.dto.UserDto
import com.google.gson.annotations.SerializedName

data class CreateUserResponse (
    @SerializedName("user")
    val user: UserDto?= null,
    @SerializedName("token")
    val token: String?= null
)