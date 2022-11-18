package com.pthw.network.feature.samplepost.mapper

import com.pthw.data.feature.samplepost.entity.PostEntity
import com.pthw.domain.mapper.UnidirectionalMap
import com.pthw.network.feature.samplepost.response.PostSource
import javax.inject.Inject

class PostNetworkToDataMapper @Inject constructor() :
    UnidirectionalMap<PostSource, PostEntity> {
    override fun map(item: PostSource): PostEntity {
        return PostEntity(
            userId = item.userId ?: 0,
            id = item.id ?: 0,
            title = item.title.orEmpty(),
            body = item.body.orEmpty()
        )
    }
}