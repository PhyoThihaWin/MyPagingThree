package com.pthw.cache.feature.article.response

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Immutable model class for an article
 */
data class ArticleSource(
    val id: Int,
    val title: String,
    val description: String,
    val created: LocalDateTime,
)

val ArticleSource.createdText: String get() = articleDateFormatter.format(created)

private val articleDateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
