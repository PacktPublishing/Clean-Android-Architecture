package com.clean.exercise0301

import retrofit2.http.GET

interface UserService {

    @GET("/users")
    suspend fun getUsers(): List<User>

}