package com.pthw.mypagingthree.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pthw.mypagingthree.data.api.SplashPhotoService
import com.pthw.mypagingthree.data.model.response.SplashPhoto
import com.pthw.mypagingthree.utils.exception.NoContentException
import com.pthw.mypagingthree.utils.ext.getBody
import com.pthw.mypagingthree.utils.ext.getBodyOrThrowNetworkException
import kotlinx.coroutines.delay
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 1
private const val LOAD_DELAY_MILLIS = 6_000L

class SplashPhotoPagingSource(
    private val service: SplashPhotoService, private val query: String
) : PagingSource<Int, SplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SplashPhoto> {
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
            Timber.e(it.message.orEmpty())
            LoadResult.Error(it)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, SplashPhoto>): Int? {
        return state.anchorPosition
    }

}