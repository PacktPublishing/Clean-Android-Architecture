package com.clean.app.injection


import com.clean.app.idling.ComposeCountingIdlingResource
import com.clean.app.repository.IdlingInteractionRepository
import com.clean.app.repository.IdlingPostRepository
import com.clean.app.repository.IdlingUserRepository
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
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class IdlingRepositoryModule {

    @Singleton
    @Provides
    fun provideIdlingResource(): ComposeCountingIdlingResource =
        ComposeCountingIdlingResource("repository-idling")

    @Provides
    fun providePostRepository(
        remotePostDataSource: RemotePostDataSource,
        localPostDataSource: LocalPostDataSource,
        countingIdlingResource: ComposeCountingIdlingResource
    ): PostRepository = IdlingPostRepository(
        PostRepositoryImpl(
            remotePostDataSource,
            localPostDataSource
        ),
        countingIdlingResource
    )

    @Provides
    fun provideUserRepository(
        remoteUserDataSource: RemoteUserDataSource,
        localUserDataSource: LocalUserDataSource,
        countingIdlingResource: ComposeCountingIdlingResource
    ): UserRepository = IdlingUserRepository(
        UserRepositoryImpl(
            remoteUserDataSource,
            localUserDataSource
        ),
        countingIdlingResource
    )

    @Provides
    fun provideInteractionRepository(
        interactionDataSource: LocalInteractionDataSource,
        countingIdlingResource: ComposeCountingIdlingResource
    ): InteractionRepository = IdlingInteractionRepository(
        InteractionRepositoryImpl(
            interactionDataSource
        ),
        countingIdlingResource
    )
}