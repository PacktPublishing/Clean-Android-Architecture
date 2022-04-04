package com.clean.app.injection

import com.clean.app.remote.MockRemotePostDataSource
import com.clean.app.remote.MockRemoteUserDataSource
import com.clean.data_remote.injection.RemoteDataSourceModule
import com.clean.data_repository.data_source.remote.RemotePostDataSource
import com.clean.data_repository.data_source.remote.RemoteUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteDataSourceModule::class]
)
abstract class MockRemoteDataSourceModule {

    @Binds
    abstract fun bindPostDataSource(postDataSourceImpl: MockRemotePostDataSource): RemotePostDataSource

    @Binds
    abstract fun bindUserDataSource(userDataSourceImpl: MockRemoteUserDataSource): RemoteUserDataSource
}