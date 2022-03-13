package com.clean.data_remote.networking.user

import com.squareup.moshi.Json

data class UserApiModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String,
    @Json(name = "email") val email: String
)