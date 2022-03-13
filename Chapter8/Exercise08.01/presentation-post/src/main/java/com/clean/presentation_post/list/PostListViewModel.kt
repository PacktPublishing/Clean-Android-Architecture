package com.clean.presentation_post.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clean.domain.usecase.GetPostsWithUsersWithInteractionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val useCase: GetPostsWithUsersWithInteractionUseCase,
    private val converter: PostListConverter
) : ViewModel() {

    private val _postListFlow =
        MutableStateFlow<UiState<PostListModel>>(UiState.Loading)
    val postListFlow: StateFlow<UiState<PostListModel>> = _postListFlow

    fun loadPosts() {
        viewModelScope.launch {
            useCase.execute(GetPostsWithUsersWithInteractionUseCase.Request)
                .map {
                    converter.convert(it)
                }
                .collect {
                    _postListFlow.value = it
                }
        }
    }
}