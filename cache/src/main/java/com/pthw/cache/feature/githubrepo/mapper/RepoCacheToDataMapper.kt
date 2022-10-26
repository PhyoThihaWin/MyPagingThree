package com.pthw.cache.feature.githubrepo.mapper

import com.pthw.cache.feature.githubrepo.entity.RepoDbEntity
import com.pthw.data.feature.githubrepo.entity.RepoEntity
import com.pthw.domain.mapper.UnidirectionalMap
import javax.inject.Inject

class RepoCacheToDataMapper @Inject constructor() :
    UnidirectionalMap<RepoDbEntity, RepoEntity> {
    override fun map(item: RepoDbEntity): RepoEntity {
        return RepoEntity(
            id = item.id,
            name = item.name,
            fullName = item.fullName,
            description = item.description,
            url = item.url,
            stars = item.stars,
            language = item.language,
            forks = item.forks
        )
    }
}