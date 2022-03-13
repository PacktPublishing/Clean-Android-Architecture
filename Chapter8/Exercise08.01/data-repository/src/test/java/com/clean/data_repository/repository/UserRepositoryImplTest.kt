package com.clean.data_repository.repository

import com.clean.data_repository.data_source.local.LocalUserDataSource
import com.clean.data_repository.data_source.remote.RemoteUserDataSource
import com.clean.domain.entity.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserRepositoryImplTest {

    private val remoteUserDataSource = mock<RemoteUserDataSource>()
    private val localUserDataSource = mock<LocalUserDataSource>()
    private val repositoryImpl = UserRepositoryImpl(remoteUserDataSource, localUserDataSource)


    @ExperimentalCoroutinesApi
    @Test
    fun testGetUsers() = runBlockingTest {
        val users = listOf(User(1, "name", "username", "email"))
        whenever(remoteUserDataSource.getUsers()).thenReturn(flowOf(users))
        val result = repositoryImpl.getUsers().first()
        assertEquals(users, result)
        verify(localUserDataSource).addUsers(users)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetUser() = runBlockingTest {
        val id = 1L
        val user = User(id, "name", "username", "email")
        whenever(remoteUserDataSource.getUser(id)).thenReturn(flowOf(user))
        val result = repositoryImpl.getUser(id).first()
        assertEquals(user, result)
        verify(localUserDataSource).addUsers(listOf(user))
    }
}