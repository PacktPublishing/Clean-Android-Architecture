package com.clean.app.repository

import com.clean.app.idling.ComposeCountingIdlingResource
import com.clean.app.idling.attachIdling
import com.clean.domain.entity.User
import com.clean.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class IdlingUserRepository(
    private val userRepository: UserRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : UserRepository {
    override fun getUsers(): Flow<List<User>> =
        userRepository.getUsers()
            .attachIdling(countingIdlingResource)

    override fun getUser(id: Long): Flow<User> =
        userRepository.getUser(id)
            .attachIdling(countingIdlingResource)
}