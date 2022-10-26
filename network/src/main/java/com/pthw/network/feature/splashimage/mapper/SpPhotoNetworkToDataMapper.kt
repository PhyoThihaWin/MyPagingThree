package com.pthw.network.feature.splashimage.mapper

import com.pthw.data.feature.splashimage.entity.SplashPhotoEntity
import com.pthw.domain.mapper.UnidirectionalMap
import com.pthw.network.feature.splashimage.response.SplashPhotoSource
import javax.inject.Inject

class SpPhotoNetworkToDataMapper @Inject constructor() :
    UnidirectionalMap<SplashPhotoSource, SplashPhotoEntity> {
    override fun map(item: SplashPhotoSource): SplashPhotoEntity {
        return SplashPhotoEntity(
            id = item.id,
            description = item.description,
            urls = SplashPhotoEntity.SplashPhotoUrls(
                raw = item.urls.raw, full = item.urls.full, regular = item.urls.regular,
                small = item.urls.small, thumb = item.urls.thumb
            ),
            user = SplashPhotoEntity.SplashUser(
                name = item.user.name, username = item.user.username
            )
        )
    }
}