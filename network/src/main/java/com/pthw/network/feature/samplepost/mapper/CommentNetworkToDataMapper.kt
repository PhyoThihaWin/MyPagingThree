package com.pthw.network.feature.samplepost.mapper

import com.pthw.data.feature.samplepost.entity.CommentEntity
import com.pthw.data.feature.samplepost.entity.PostEntity
import com.pthw.domain.mapper.UnidirectionalMap
import com.pthw.network.feature.samplepost.response.CommentSource
import com.pthw.network.feature.samplepost.response.PostSource
import javax.inject.Inject

class CommentNetworkToDataMapper @Inject constructor() :
    UnidirectionalMap<CommentSource, CommentEntity> {
    override fun map(item: CommentSource): CommentEntity {
        return CommentEntity(
            postId = item.postId ?: 0,
            id = item.id ?: 0,
            name = item.name.orEmpty(),
            email = item.email.orEmpty(),
            body = item.body.orEmpty()
        )
    }
}