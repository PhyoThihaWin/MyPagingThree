package com.pthw.domain.feature.samplepost.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
) : java.io.Serializable
