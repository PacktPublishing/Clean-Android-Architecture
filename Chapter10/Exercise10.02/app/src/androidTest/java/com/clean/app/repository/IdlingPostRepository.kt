package com.clean.app.repository

import com.clean.app.idling.ComposeCountingIdlingResource
import com.clean.app.idling.attachIdling
import com.clean.domain.entity.Post
import com.clean.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class IdlingPostRepository(
    private val postRepository: PostRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : PostRepository {
    override fun getPosts(): Flow<List<Post>> =
        postRepository.getPosts().attachIdling(countingIdlingResource)

    override fun getPost(id: Long): Flow<Post> =
        postRepository.getPost(id).attachIdling(countingIdlingResource)
}