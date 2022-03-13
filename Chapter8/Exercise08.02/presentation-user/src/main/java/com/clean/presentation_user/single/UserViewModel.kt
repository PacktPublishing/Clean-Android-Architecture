package com.clean.presentation_user.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clean.domain.usecase.GetUserUseCase
import com.clean.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: GetUserUseCase,
    private val converter: UserConverter
) : ViewModel() {

    private val _userFlow =
        MutableStateFlow<UiState<UserModel>>(UiState.Loading)
    val userFlow: StateFlow<UiState<UserModel>> = _userFlow

    fun loadUser(userId: Long) {
        viewModelScope.launch {
            userUseCase.execute(GetUserUseCase.Request(userId))
                .map {
                    converter.convert(it)
                }
                .collect {
                    _userFlow.value = it
                }
        }
    }
}