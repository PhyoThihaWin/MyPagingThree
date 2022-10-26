package com.pthw.network.feature.splashimage.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pthw.network.extension.getBody
import com.pthw.network.feature.splashimage.response.SplashPhotoSource
import com.pthw.network.feature.splashimage.service.SplashPhotoService
import kotlinx.coroutines.delay
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 1
private const val LOAD_DELAY_MILLIS = 6_000L

class SplashPhotoPagingSource(
    private val query: String,
    private val service: SplashPhotoService,
) : PagingSource<Int, SplashPhotoSource>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SplashPhotoSource> {
        val startKey = params.key ?: STARTING_PAGE_INDEX

        if (startKey != STARTING_PAGE_INDEX) {
            Timber.i("Reached delay phrase.....")
            delay(LOAD_DELAY_MILLIS)
        }

        return runCatching {
            val raw = service.searchPhotos(query, startKey, params.loadSize).getBody()
            val results = raw.data.orEmpty()
            Timber.e("Success api-call: %s", results.size)
            LoadResult.Page(
                data = results,
                prevKey = if (startKey == STARTING_PAGE_INDEX) null else startKey - 1,
                nextKey = if (results.isEmpty()) null else startKey + 1
            )
        }.getOrElse {
            LoadResult.Error(it)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, SplashPhotoSource>): Int? {
        return state.anchorPosition
    }

}