package com.clean.domain.usecase

import com.clean.domain.entity.Interaction
import com.clean.domain.entity.Post
import com.clean.domain.entity.PostWithUser
import com.clean.domain.entity.User
import com.clean.domain.repository.InteractionRepository
import com.clean.domain.repository.PostRepository
import com.clean.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class GetPostsWithUsersWithInteractionUseCaseTest {

    private val postRepository = mock<PostRepository>()
    private val userRepository = mock<UserRepository>()
    private val interactionRepository = mock<InteractionRepository>()
    private val useCase = GetPostsWithUsersWithInteractionUseCase(
        mock(),
        postRepository,
        userRepository,
        interactionRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runBlockingTest {
        val user1 = User(1L, "name1", "username1", "email1")
        val user2 = User(2L, "name2", "username2", "email2")
        val post1 = Post(1L, user1.id, "title1", "body1")
        val post2 = Post(2L, user1.id, "title2", "body2")
        val post3 = Post(3L, user2.id, "title3", "body3")
        val post4 = Post(4L, user2.id, "title4", "body4")
        val interaction = Interaction(10)
        whenever(userRepository.getUsers()).thenReturn(flowOf(listOf(user1, user2)))
        whenever(postRepository.getPosts()).thenReturn(flowOf(listOf(post1, post2, post3, post4)))
        whenever(interactionRepository.getInteraction()).thenReturn(flowOf(interaction))
        val response = useCase.process(GetPostsWithUsersWithInteractionUseCase.Request).first()
        assertEquals(
            GetPostsWithUsersWithInteractionUseCase.Response(
                listOf(
                    PostWithUser(post1, user1),
                    PostWithUser(post2, user1),
                    PostWithUser(post3, user2),
                    PostWithUser(post4, user2),
                ), interaction
            ),
            response
        )
    }
}