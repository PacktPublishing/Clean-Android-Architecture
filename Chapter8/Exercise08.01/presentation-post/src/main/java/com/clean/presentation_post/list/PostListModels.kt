package com.clean.presentation_post.list

data class PostListModel(
    val headerText: String = "",
    val items: List<PostListItemModel> = listOf()
)

data class PostListItemModel(
    val id: Long,
    val authorName: String,
    val title: String
)