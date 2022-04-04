package com.clean.presentation_user.single

import com.clean.presentation_common.state.UiAction

sealed class UserUiAction : UiAction {

    data class Load(val userId: Long) : UserUiAction()
}