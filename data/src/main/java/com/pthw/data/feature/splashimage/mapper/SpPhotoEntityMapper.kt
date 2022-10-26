package com.pthw.data.feature.splashimage.mapper

import com.pthw.data.feature.splashimage.entity.SplashPhotoEntity
import com.pthw.domain.mapper.UnidirectionalMap
import com.pthw.domain.feature.splashimage.model.SplashPhoto
import javax.inject.Inject

class SpPhotoEntityMapper @Inject constructor() :
    UnidirectionalMap<SplashPhotoEntity, SplashPhoto> {
    override fun map(item: SplashPhotoEntity): SplashPhoto {
        return SplashPhoto(
            id = item.id,
            description = item.description,
            urls = SplashPhoto.SplashPhotoUrls(
                raw = item.urls.raw, full = item.urls.full, regular = item.urls.regular,
                small = item.urls.small, thumb = item.urls.thumb
            ),
            user = SplashPhoto.SplashUser(
                name = item.user.name, username = item.user.username
            )
        )
    }
}