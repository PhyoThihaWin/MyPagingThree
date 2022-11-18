package com.pthw.data.feature.samplepost.mapper

import com.pthw.data.feature.samplepost.entity.PostEntity
import com.pthw.domain.feature.samplepost.model.Post
import com.pthw.domain.mapper.UnidirectionalMap
import javax.inject.Inject

class PostEntityMapper @Inject constructor() :
    UnidirectionalMap<PostEntity, Post> {
    override fun map(item: PostEntity): Post {
        return Post(
            userId = item.userId,
            id = item.id,
            title = item.title,
            body = item.body
        )
    }
}