package com.clean.data_repository.repository

import com.clean.data_repository.data_source.local.LocalPostDataSource
import com.clean.data_repository.data_source.remote.RemotePostDataSource
import com.clean.domain.entity.Post
import com.clean.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val remotePostDataSource: RemotePostDataSource,
    private val localPostDataSource: LocalPostDataSource
) : PostRepository {

    override fun getPosts(): Flow<List<Post>> = remotePostDataSource.getPosts()
        .onEach {
            localPostDataSource.addPosts(it)
        }

    override fun getPost(id: Long): Flow<Post> = remotePostDataSource.getPost(id)
        .onEach {
            localPostDataSource.addPosts(listOf(it))
        }
}