package com.clean.data_local.source

import com.clean.data_local.db.user.UserDao
import com.clean.data_local.db.user.UserEntity
import com.clean.domain.entity.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalUserDataSourceImplTest {

    private val userDao = mock<UserDao>()
    private val userDataSource = LocalUserDataSourceImpl(userDao)

    @ExperimentalCoroutinesApi
    @Test
    fun testGetUsers() = runBlockingTest {
        val localUsers = listOf(UserEntity(1, "name", "username", "email"))
        val expectedUsers = listOf(User(1, "name", "username", "email"))
        whenever(userDao.getUsers()).thenReturn(flowOf(localUsers))
        val result = userDataSource.getUsers().first()
        Assert.assertEquals(expectedUsers, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAddUsers() = runBlockingTest {
        val localUsers = listOf(UserEntity(1, "name", "username", "email"))
        val users = listOf(User(1, "name", "username", "email"))
        userDataSource.addUsers(users)
        verify(userDao).insertUsers(localUsers)
    }

}