package com.pthw.mypagingthree.feature.splashimage.ui

import androidx.paging.PagingData
import com.pthw.domain.feature.splashimage.model.SplashPhoto

sealed class SpPhotoViewState {
    data class SuccessState(val data: PagingData<SplashPhoto>) : SpPhotoViewState()
    data class ErrorState(val msg: String)
}
