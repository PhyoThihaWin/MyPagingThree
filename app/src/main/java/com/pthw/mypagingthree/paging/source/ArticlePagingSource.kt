package com.pthw.mypagingthree.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pthw.mypagingthree.data.model.Article
import kotlinx.coroutines.delay
import timber.log.Timber
import java.time.LocalDateTime
import kotlin.math.max

private const val STARTING_KEY = 0
private const val LOAD_DELAY_MILLIS = 6_000L

private val firstArticleCreatedTime = LocalDateTime.now()

class ArticlePagingSource: PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = article.id - (state.config.pageSize / 2))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val startKey = params.key ?: STARTING_KEY
        val range = startKey.until(startKey + params.loadSize)

        if (startKey != STARTING_KEY) {
            Timber.i("Reached delay phrase.....")
            delay(LOAD_DELAY_MILLIS)
        }

        Timber.i("Reached Time to Load...%s %s %s", startKey, params.loadSize, range.count())

        return LoadResult.Page(
            data = range.map { number ->
                Article(
                    id = number,
                    title = "Article $number",
                    description = "This describes article $number",
                    created = firstArticleCreatedTime.minusDays(number.toLong())
                )
            },
            prevKey = when (startKey) {
                STARTING_KEY -> null
                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
                    // We're at the start, there's nothing more to load
                    STARTING_KEY -> null
                    else -> prevKey
                }
            },
            nextKey = range.last + 1
        )
    }

    /**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}