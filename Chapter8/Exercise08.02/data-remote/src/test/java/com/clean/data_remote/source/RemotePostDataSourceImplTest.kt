package com.clean.data_remote.source

import com.clean.data_remote.networking.post.PostApiModel
import com.clean.data_remote.networking.post.PostService
import com.clean.domain.entity.Post
import com.clean.domain.entity.UseCaseException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemotePostDataSourceImplTest {

    private val postService = mock<PostService>()
    private val postDataSource = RemotePostDataSourceImpl(postService)

    @ExperimentalCoroutinesApi
    @Test
    fun testGetPosts() = runBlockingTest {
        val remotePosts = listOf(PostApiModel(1, 1, "title", "body"))
        val expectedPosts = listOf(Post(1, 1, "title", "body"))
        whenever(postService.getPosts()).thenReturn(remotePosts)
        val result = postDataSource.getPosts().first()
        Assert.assertEquals(expectedPosts, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetPost() = runBlockingTest {
        val id = 1L
        val remotePost = PostApiModel(id, 1, "title", "body")
        val expectedPost = Post(id, 1, "title", "body")
        whenever(postService.getPost(id)).thenReturn(remotePost)
        val result = postDataSource.getPost(id).first()
        Assert.assertEquals(expectedPost, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetPostsThrowsError() = runBlockingTest {
        whenever(postService.getPosts()).thenThrow(RuntimeException())
        postDataSource.getPosts().catch {
            Assert.assertTrue(it is UseCaseException.PostException)
        }.collect()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetPostThrowsError() = runBlockingTest {
        val id = 1L
        whenever(postService.getPost(id)).thenThrow(RuntimeException())
        postDataSource.getPost(id).catch {
            Assert.assertTrue(it is UseCaseException.PostException)
        }.collect()
    }
}