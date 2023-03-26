package com.clean.data_local.source

import com.clean.data_local.db.post.PostDao
import com.clean.data_local.db.post.PostEntity
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

class LocalPostDataSourceImplTest {

    private val postDao = mock<PostDao>()
    private val postDataSource = LocalPostDataSourceImpl(postDao)

    @ExperimentalCoroutinesApi
    @Test
    fun testGetPosts() = runBlockingTest {
        val localPosts = listOf(PostEntity(1, 1, "title", "body"))
        val expectedPosts = listOf(Post(1, 1, "title", "body"))
        whenever(postDao.getPosts()).thenReturn(flowOf(localPosts))
        val result = postDataSource.getPosts().first()
        Assert.assertEquals(expectedPosts, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAddUsers() = runBlockingTest {
        val localPosts = listOf(PostEntity(1, 1, "title", "body"))
        val posts = listOf(Post(1, 1, "title", "body"))
        postDataSource.addPosts(posts)
        verify(postDao).insertPosts(localPosts)
    }

}