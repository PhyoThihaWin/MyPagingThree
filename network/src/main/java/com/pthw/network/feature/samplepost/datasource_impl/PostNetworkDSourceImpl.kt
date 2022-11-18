package com.pthw.network.feature.samplepost.datasource_impl

import com.pthw.data.feature.samplepost.datasource.PostNetworkDSource
import com.pthw.data.feature.samplepost.entity.CommentEntity
import com.pthw.data.feature.samplepost.entity.PostEntity
import com.pthw.network.extension.getBody
import com.pthw.network.feature.samplepost.mapper.CommentNetworkToDataMapper
import com.pthw.network.feature.samplepost.mapper.PostNetworkToDataMapper
import com.pthw.network.feature.samplepost.service.PostService
import javax.inject.Inject

class PostNetworkDSourceImpl @Inject constructor(
    private val service: PostService,
    private val mapper: PostNetworkToDataMapper,
    private val cmMapper: CommentNetworkToDataMapper
) :
    PostNetworkDSource {
    override suspend fun getLatestPost(): List<PostEntity> {
        val raw = service.getLatestPost().getBody()
        return raw.map(mapper::map)
    }

    override suspend fun getPostDetail(postId: Int): List<CommentEntity> {
        val raw = service.getPostDetail(postId = postId).getBody()
        return raw.map(cmMapper::map)
    }

}