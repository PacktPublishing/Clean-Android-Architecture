package com.clean.app.remote

import com.clean.data_repository.data_source.remote.RemotePostDataSource
import com.clean.domain.entity.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MockRemotePostDataSource @Inject constructor() : RemotePostDataSource {

    private val posts = listOf(
        Post(
            id = 1L,
            userId = 1L,
            title = "title1",
            body = "body1"
        ),
        Post(
            id = 2L,
            userId = 1L,
            title = "title2",
            body = "body2"
        ),
        Post(
            id = 3L,
            userId = 2L,
            title = "title3",
            body = "body3"
        ),
        Post(
            id = 4L,
            userId = 2L,
            title = "title4",
            body = "body4"
        )
    )


    override fun getPosts(): Flow<List<Post>> = flowOf(posts)

    override fun getPost(id: Long): Flow<Post> = flowOf(posts[0])
}