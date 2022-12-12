package com.pthw.domain.feature.samplepost.repository

import com.pthw.domain.feature.samplepost.model.Comment
import com.pthw.domain.feature.samplepost.model.Post

interface PostRepository {
    suspend fun getLatestPost(): List<Post>
    suspend fun getPostDetail(postId: Int): List<Comment>
}
