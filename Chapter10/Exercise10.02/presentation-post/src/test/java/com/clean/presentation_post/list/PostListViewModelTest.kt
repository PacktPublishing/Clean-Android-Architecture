package com.clean.presentation_post.list

import com.clean.domain.entity.Interaction
import com.clean.domain.entity.Result
import com.clean.domain.usecase.GetPostsWithUsersWithInteractionUseCase
import com.clean.domain.usecase.UpdateInteractionUseCase
import com.clean.presentation_common.navigation.NavRoutes
import com.clean.presentation_common.navigation.PostInput
import com.clean.presentation_common.navigation.UserInput
import com.clean.presentation_common.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
    private lateinit var viewModel: PostListViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = PostListViewModel(
            getPostsWithUsersWithInteractionUseCase,
            converter,
            updateInteractionUseCase
        )
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testHandleActionLoad() = runBlockingTest {
        assertEquals(UiState.Loading, viewModel.uiStateFlow.value)
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
        viewModel.handleAction(PostListUiAction.Load)
        assertEquals(uiState, viewModel.uiStateFlow.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testHandleActionPostClick() = runBlockingTest {
        val postId = 3L
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
        viewModel.handleAction(PostListUiAction.PostClick(postId, interaction))
        verify(updateInteractionUseCase).execute(
            UpdateInteractionUseCase.Request(
                interaction.copy(
                    interaction.totalClicks + 1
                )
            )
        )
        assertEquals(
            PostListUiSingleEvent.OpenPostScreen(
                NavRoutes.Post.routeForPost(
                    PostInput(postId)
                )
            ), viewModel.singleEventFlow.first()
        )

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testHandleActionUserClick() = runBlockingTest {
        val userId = 3L
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
        viewModel.handleAction(PostListUiAction.UserClick(userId, interaction))
        verify(updateInteractionUseCase).execute(
            UpdateInteractionUseCase.Request(
                interaction.copy(
                    interaction.totalClicks + 1
                )
            )
        )
        assertEquals(
            PostListUiSingleEvent.OpenUserScreen(
                NavRoutes.User.routeForUser(
                    UserInput(userId)
                )
            ), viewModel.singleEventFlow.first()
        )

    }
}