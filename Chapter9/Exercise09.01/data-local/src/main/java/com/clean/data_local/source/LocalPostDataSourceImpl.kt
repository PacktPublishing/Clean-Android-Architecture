package com.clean.data_local.source

import com.clean.data_local.db.post.PostDao
import com.clean.data_local.db.post.PostEntity
import com.clean.data_repository.data_source.local.LocalPostDataSource
import com.clean.domain.entity.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPostDataSourceImpl @Inject constructor(private val postDao: PostDao) :
    LocalPostDataSource {

    override fun getPosts(): Flow<List<Post>> = postDao.getPosts().map { posts ->
        posts.map {
            Post(it.id, it.userId, it.title, it.body)
        }
    }

    override suspend fun addPosts(posts: List<Post>) = postDao.insertPosts(posts.map {
        PostEntity(it.id, it.userId, it.title, it.body)
    })
}