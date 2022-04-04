package com.clean.presentation_post.list

import android.content.Context
import com.clean.domain.entity.Interaction
import com.clean.domain.entity.Post
import com.clean.domain.entity.PostWithUser
import com.clean.domain.entity.User
import com.clean.domain.usecase.GetPostsWithUsersWithInteractionUseCase
import com.clean.presentation_post.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PostListConverterTest {

    private val context = mock<Context>()
    private val converter = PostListConverter(context)

    @Test
    fun testConvertSuccess() {
        val response = GetPostsWithUsersWithInteractionUseCase.Response(
            posts = listOf(
                PostWithUser(
                    post = Post(
                        id = 1L,
                        userId = 1L,
                        title = "title",
                        body = "body"
                    ),
                    user = User(id = 1L, name = "name", username = "username", email = "email")
                )
            ),
            interaction = Interaction(10)
        )
        val formattedClickText = "formattedClickText"
        val formattedAuthorName = "formattedAuthorName"
        val formattedPostTitle = "formattedPostTitle"
        whenever(context.getString(R.string.total_click_count, 10)).thenReturn(formattedClickText)
        whenever(context.getString(R.string.author, "name")).thenReturn(formattedAuthorName)
        whenever(context.getString(R.string.title, "title")).thenReturn(formattedPostTitle)
        val result = converter.convertSuccess(response)
        assertEquals(
            PostListModel(
                headerText = formattedClickText,
                items = listOf(
                    PostListItemModel(
                        id = 1L,
                        authorName = formattedAuthorName,
                        title = formattedPostTitle
                    )
                ),
                interaction = response.interaction
            ), result
        )
    }
}