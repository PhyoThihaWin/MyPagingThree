package com.pthw.data.feature.article.mapper

import com.pthw.data.feature.article.entity.ArticleEntity
import com.pthw.domain.feature.article.model.Article
import com.pthw.domain.mapper.UnidirectionalMap
import javax.inject.Inject

class ArticleEntityMapper @Inject constructor():
UnidirectionalMap<ArticleEntity, Article>{
    override fun map(item: ArticleEntity): Article {
        return Article(
            id = item.id,
            title = item.title,
            description = item.description,
            created = item.created,
            createdText = item.createdText
        )
    }
}