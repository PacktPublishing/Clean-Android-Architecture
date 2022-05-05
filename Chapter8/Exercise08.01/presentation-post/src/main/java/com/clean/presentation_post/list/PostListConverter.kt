package com.clean.presentation_post.list

import android.content.Context
import com.clean.domain.entity.Result
import com.clean.domain.usecase.GetPostsWithUsersWithInteractionUseCase
import com.clean.presentation_post.R

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PostListConverter @Inject constructor(@ApplicationContext private val context: Context) {

    fun convert(postListResult: Result<GetPostsWithUsersWithInteractionUseCase.Response>): UiState<PostListModel> {
        return when (postListResult) {
            is Result.Error -> {
                UiState.Error(postListResult.exception.localizedMessage.orEmpty())
            }
            is Result.Success -> {
                UiState.Success(PostListModel(
                    headerText = context.getString(
                        R.string.total_click_count,
                        postListResult.data.interaction.totalClicks
                    ),
                    items = postListResult.data.posts.map {
                        PostListItemModel(
                            it.post.id,
                            it.user.id,
                            context.getString(R.string.author, it.user.name),
                            context.getString(R.string.title, it.post.title)
                        )
                    }
                ))
            }
        }
    }
}