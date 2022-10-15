package com.pthw.mypagingthree.ui

import androidx.paging.PagingData
import com.pthw.mypagingthree.data.model.response.SplashPhoto

sealed class ImageViewState {
    data class SuccessState(val data: PagingData<SplashPhoto>) : ImageViewState()
    data class ErrorState(val msg: String)
}
