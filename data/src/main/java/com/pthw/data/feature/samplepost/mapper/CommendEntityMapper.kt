package com.pthw.data.feature.samplepost.mapper

import com.pthw.data.feature.samplepost.entity.CommentEntity
import com.pthw.domain.feature.samplepost.model.Comment
import com.pthw.domain.mapper.UnidirectionalMap
import javax.inject.Inject

class CommendEntityMapper @Inject constructor() :
    UnidirectionalMap<CommentEntity, Comment> {
    override fun map(item: CommentEntity): Comment {
        return Comment(
            postId = item.postId,
            id = item.id,
            name = item.name,
            email = item.email,
            body = item.body
        )
    }
}