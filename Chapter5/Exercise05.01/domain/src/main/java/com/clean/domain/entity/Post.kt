package com.clean.domain.entity

data class Post(
    val id: Long,
    val userId: Long,
    val title: String,
    val body: String
)