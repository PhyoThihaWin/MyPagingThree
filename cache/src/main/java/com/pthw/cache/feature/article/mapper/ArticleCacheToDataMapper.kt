package com.pthw.cache.feature.article.mapper

import com.pthw.data.feature.article.entity.ArticleEntity
import com.pthw.domain.mapper.UnidirectionalMap
import com.pthw.cache.feature.article.response.ArticleSource
import com.pthw.cache.feature.article.response.createdText
import javax.inject.Inject

class ArticleCacheToDataMapper @Inject constructor():
UnidirectionalMap<ArticleSource, ArticleEntity>{
    override fun map(item: ArticleSource): ArticleEntity {
        return ArticleEntity(
            id = item.id,
            title = item.title,
            description = item.description,
            item.created,
            createdText = item.createdText
        )
    }
}