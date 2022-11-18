package com.pthw.data.feature.samplepost.repository_impl

import com.pthw.data.feature.samplepost.datasource.PostNetworkDSource
import com.pthw.data.feature.samplepost.mapper.CommendEntityMapper
import com.pthw.data.feature.samplepost.mapper.PostEntityMapper
import com.pthw.domain.feature.samplepost.model.Comment
import com.pthw.domain.feature.samplepost.model.Post
import com.pthw.domain.feature.samplepost.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val dataSource: PostNetworkDSource,
    private val mapper: PostEntityMapper,
    private val cmMapper: CommendEntityMapper
) : PostRepository {

    override suspend fun getLatestPost(): List<Post> {
        return dataSource.getLatestPost().map(mapper::map)
    }

    override suspend fun getPostDetail(postId: Int): List<Comment> {
        return dataSource.getPostDetail(postId).map(cmMapper::map)
    }

}