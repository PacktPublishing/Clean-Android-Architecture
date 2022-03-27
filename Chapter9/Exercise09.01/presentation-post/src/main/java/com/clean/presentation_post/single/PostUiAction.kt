package com.clean.presentation_post.single

import com.clean.presentation_common.state.UiAction

sealed class PostUiAction : UiAction {

    data class Load(val postId: Long) : PostUiAction()
}