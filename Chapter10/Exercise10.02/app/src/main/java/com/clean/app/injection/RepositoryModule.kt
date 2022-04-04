package com.clean.app.injection

import com.clean.data_repository.data_source.local.LocalInteractionDataSource
import com.clean.data_repository.data_source.local.LocalPostDataSource
import com.clean.data_repository.data_source.local.LocalUserDataSource
import com.clean.data_repository.data_source.remote.RemotePostDataSource
import com.clean.data_repository.data_source.remote.RemoteUserDataSource
import com.clean.data_repository.repository.InteractionRepositoryImpl
import com.clean.data_repository.repository.PostRepositoryImpl
import com.clean.data_repository.repository.UserRepositoryImpl
import com.clean.domain.repository.InteractionRepository
import com.clean.domain.repository.PostRepository
import com.clean.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providePostRepository(
        remotePostDataSource: RemotePostDataSource,
        localPostDataSource: LocalPostDataSource
    ): PostRepository = PostRepositoryImpl(
        remotePostDataSource,
        localPostDataSource
    )

    @Provides
    fun provideUserRepository(
        remoteUserDataSource: RemoteUserDataSource,
        localUserDataSource: LocalUserDataSource
    ): UserRepository = UserRepositoryImpl(
        remoteUserDataSource,
        localUserDataSource
    )

    @Provides
    fun provideInteractionRepository(
        interactionDataSource: LocalInteractionDataSource
    ): InteractionRepository = InteractionRepositoryImpl(
        interactionDataSource
    )
}