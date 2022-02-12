package com.clean.domain.usecase

import com.clean.domain.entity.Interaction
import com.clean.domain.entity.PostWithUser
import com.clean.domain.repository.InteractionRepository
import com.clean.domain.repository.PostRepository
import com.clean.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetPostsWithUsersWithInteractionUseCase @Inject constructor(
    configuration: Configuration,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val interactionRepository: InteractionRepository
) : UseCase<GetPostsWithUsersWithInteractionUseCase.Request,
        GetPostsWithUsersWithInteractionUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        combine(
            postRepository.getPosts(),
            userRepository.getUsers(),
            interactionRepository.getInteraction()
        ) { posts, users, interaction ->
            val postUsers = posts.map { post ->
                val user = users.first {
                    it.id == post.userId
                }
                PostWithUser(post, user)
            }
            Response(postUsers, interaction)
        }

    object Request : UseCase.Request

    data class Response(
        val posts: List<PostWithUser>,
        val interaction: Interaction
    ) : UseCase.Response
}