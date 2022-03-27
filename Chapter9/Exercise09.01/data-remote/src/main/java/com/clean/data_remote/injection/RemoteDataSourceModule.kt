package com.clean.data_remote.injection

import com.clean.data_remote.source.RemotePostDataSourceImpl
import com.clean.data_remote.source.RemoteUserDataSourceImpl
import com.clean.data_repository.data_source.remote.RemotePostDataSource
import com.clean.data_repository.data_source.remote.RemoteUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindPostDataSource(postDataSourceImpl: RemotePostDataSourceImpl): RemotePostDataSource

    @Binds
    abstract fun bindUserDataSource(userDataSourceImpl: RemoteUserDataSourceImpl): RemoteUserDataSource
}