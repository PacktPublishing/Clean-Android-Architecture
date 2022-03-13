package com.clean.presentation_post.single

import com.clean.domain.entity.Result
import com.clean.domain.usecase.GetPostUseCase
import com.clean.presentation_common.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PostViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val useCase = mock<GetPostUseCase>()
    private val converter = mock<PostConverter>()
    private val viewModel = PostViewModel(useCase, converter)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testLoadPost() = runBlockingTest {
        assertEquals(UiState.Loading, viewModel.postFlow.value)
        val postId = 1L
        val uiState = mock<UiState<PostModel>>()
        val result = mock<Result<GetPostUseCase.Response>>()
        whenever(useCase.execute(GetPostUseCase.Request(postId))).thenReturn(
            flowOf(
                result
            )
        )
        whenever(converter.convert(result)).thenReturn(uiState)
        viewModel.loadPost(postId)
        assertEquals(uiState, viewModel.postFlow.value)
    }
}