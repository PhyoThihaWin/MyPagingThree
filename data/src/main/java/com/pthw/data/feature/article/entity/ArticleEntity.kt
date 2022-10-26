package com.pthw.data.feature.article.entity

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Immutable model class for an article
 */
data class ArticleEntity(
    val id: Int,
    val title: String,
    val description: String,
    val created: LocalDateTime,
    val createdText: String
)

