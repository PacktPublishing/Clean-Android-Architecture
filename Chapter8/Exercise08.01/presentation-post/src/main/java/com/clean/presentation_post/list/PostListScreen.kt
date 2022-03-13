package com.clean.presentation_post.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PostListScreen(
    viewModel: PostListViewModel
) {
    viewModel.loadPosts()
    viewModel.postListFlow.collectAsState().value.let { state ->
        when (state) {
            is UiState.Loading -> {
                Loading()
            }
            is UiState.Error -> {
                Error(state.errorMessage)
            }
            is UiState.Success -> {
                PostList(state.data)
            }
        }
    }
}


@Composable
fun PostList(
    postListModel: PostListModel
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item(postListModel.headerText) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = postListModel.headerText)
            }
        }
        items(postListModel.items) { item ->
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = item.authorName)
                Text(text = item.title)
            }
        }
    }
}

@Composable
fun Error(errorMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Snackbar {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}