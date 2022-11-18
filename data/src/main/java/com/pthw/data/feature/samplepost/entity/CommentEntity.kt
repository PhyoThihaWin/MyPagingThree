package com.pthw.data.feature.samplepost.entity

data class CommentEntity(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
