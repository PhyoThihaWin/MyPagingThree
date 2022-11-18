package com.pthw.data.feature.samplepost.datasource

import com.pthw.data.feature.samplepost.entity.CommentEntity
import com.pthw.data.feature.samplepost.entity.PostEntity

interface PostNetworkDSource {
    suspend fun getLatestPost(): List<PostEntity>
    suspend fun getPostDetail(postId: Int): List<CommentEntity>
}