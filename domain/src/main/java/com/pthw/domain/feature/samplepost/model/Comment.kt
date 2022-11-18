package com.pthw.domain.feature.samplepost.model

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
