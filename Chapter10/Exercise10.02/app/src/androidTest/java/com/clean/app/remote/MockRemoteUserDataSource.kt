package com.clean.app.remote

import com.clean.data_repository.data_source.remote.RemoteUserDataSource
import com.clean.domain.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MockRemoteUserDataSource @Inject constructor() : RemoteUserDataSource {

    private val users = listOf(
        User(
            id = 1L,
            name = "name1",
            username = "username1",
            email = "email1"
        ),
        User(
            id = 2L,
            name = "name2",
            username = "username2",
            email = "email2"
        )
    )


    override fun getUsers(): Flow<List<User>> = flowOf(users)

    override fun getUser(id: Long): Flow<User> = flowOf(users[0])
}