package com.clean.data_repository.repository

import com.clean.data_repository.data_source.local.LocalPostDataSource
import com.clean.data_repository.data_source.remote.RemotePostDataSource
import com.clean.domain.entity.Post
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class PostRepositoryImplTest {

    private val remotePostDataSource = mock<RemotePostDataSource>()
    private val localPostDataSource = mock<LocalPostDataSource>()
    private val repositoryImpl = PostRepositoryImpl(remotePostDataSource, localPostDataSource)


    @ExperimentalCoroutinesApi
    @Test
    fun testGetPosts() = runBlockingTest {
        val posts = listOf(Post(1, 1, "title", "body"))
        whenever(remotePostDataSource.getPosts()).thenReturn(flowOf(posts))
        val result = repositoryImpl.getPosts().first()
        Assert.assertEquals(posts, result)
        verify(localPostDataSource).addPosts(posts)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetPost() = runBlockingTest {
        val id = 1L
        val post = Post(id, 1, "title", "body")
        whenever(remotePostDataSource.getPost(id)).thenReturn(flowOf(post))
        val result = repositoryImpl.getPost(id).first()
        Assert.assertEquals(post, result)
        verify(localPostDataSource).addPosts(listOf(post))
    }
}