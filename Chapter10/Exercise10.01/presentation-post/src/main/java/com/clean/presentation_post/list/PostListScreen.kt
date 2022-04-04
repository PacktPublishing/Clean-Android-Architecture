package com.clean.presentation_post.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.clean.presentation_common.state.CommonScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostListScreen(
    viewModel: PostListViewModel,
    navController: NavController
) {
    viewModel.submitAction(PostListUiAction.Load)
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            PostList(it, { postListItem ->
                viewModel.submitAction(PostListUiAction.PostClick(postListItem.id, it.interaction))
            }) { postListItem ->
                viewModel.submitAction(PostListUiAction.UserClick(postListItem.id, it.interaction))
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is PostListUiSingleEvent.OpenPostScreen -> {
                    navController.navigate(it.navRoute)
                }
                is PostListUiSingleEvent.OpenUserScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }

}


@Composable
fun PostList(
    postListModel: PostListModel,
    onRowClick: (PostListItemModel) -> Unit,
    onAuthorClick: (PostListItemModel) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item(postListModel.headerText) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = postListModel.headerText)
            }
        }
        items(postListModel.items) { item ->
            Column(modifier = Modifier
                .padding(16.dp)
                .clickable {
                    onRowClick(item)
                }) {
                ClickableText(text = AnnotatedString(text = item.authorName), onClick = {
                    onAuthorClick(item)
                })
                Text(text = item.title)
            }
        }
    }
}