package com.clean.data_repository.injection

import com.clean.data_repository.repository.InteractionRepositoryImpl
import com.clean.data_repository.repository.PostRepositoryImpl
import com.clean.data_repository.repository.UserRepositoryImpl
import com.clean.domain.repository.InteractionRepository
import com.clean.domain.repository.PostRepository
import com.clean.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindInteractionRepository(interactionRepositoryImpl: InteractionRepositoryImpl): InteractionRepository
}