package com.clean.domain.entity

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    class Error(val exception: UseCaseException) : Result<Nothing>()
}