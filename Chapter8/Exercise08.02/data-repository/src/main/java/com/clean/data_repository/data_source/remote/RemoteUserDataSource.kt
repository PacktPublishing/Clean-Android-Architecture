package com.clean.data_repository.data_source.remote

import com.clean.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface RemoteUserDataSource {

    fun getUsers(): Flow<List<User>>

    fun getUser(id: Long): Flow<User>
}