package com.clean.presentation_post.list

import com.clean.domain.entity.Interaction
import com.clean.domain.entity.Result
import com.clean.domain.usecase.GetPostsWithUsersWithInteractionUseCase
import com.clean.domain.usecase.UpdateInteractionUseCase
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PostListViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val getPostsWithUsersWithInteractionUseCase =
        mock<GetPostsWithUsersWithInteractionUseCase>()
    private val converter = mock<PostListConverter>()
    private val updateInteractionUseCase = mock<UpdateInteractionUseCase>()
    private val viewModel = PostListViewModel(
        getPostsWithUsersWithInteractionUseCase,
        converter,
        updateInteractionUseCase
    )

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
    fun testLoadPosts() = runBlockingTest {
        assertEquals(UiState.Loading, viewModel.postListFlow.value)
        val uiState = mock<UiState<PostListModel>>()
        val result = mock<Result<GetPostsWithUsersWithInteractionUseCase.Response>>()
        whenever(
            getPostsWithUsersWithInteractionUseCase.execute(
                GetPostsWithUsersWithInteractionUseCase.Request
            )
        ).thenReturn(
            flowOf(
                result
            )
        )
        whenever(converter.convert(result)).thenReturn(uiState)
        viewModel.loadPosts()
        assertEquals(uiState, viewModel.postListFlow.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testUpdateInteraction() = runBlockingTest {
        val interaction = Interaction(10)
        whenever(
            updateInteractionUseCase.execute(
                UpdateInteractionUseCase.Request(
                    interaction.copy(
                        interaction.totalClicks + 1
                    )
                )
            )
        ).thenReturn(
            flowOf(
                Result.Success(UpdateInteractionUseCase.Response)
            )
        )
        viewModel.updateInteraction(interaction)
        verify(updateInteractionUseCase).execute(
            UpdateInteractionUseCase.Request(
                interaction.copy(
                    interaction.totalClicks + 1
                )
            )
        )
    }
}