package com.pthw.cache.feature.githubrepo.mapper

import com.pthw.cache.feature.githubrepo.entity.RepoDbEntity
import com.pthw.domain.mapper.UnidirectionalMap
import com.pthw.network.feature.githubrepo.response.RepoSource
import javax.inject.Inject

class RepoNetworkToCacheMapper @Inject constructor() :
    UnidirectionalMap<RepoSource, RepoDbEntity> {
    override fun map(item: RepoSource): RepoDbEntity {
        return RepoDbEntity(
            id = item.id,
            name = item.name,
            fullName = item.fullName,
            description = item.description,
            url = item.url,
            stars = item.stars,
            forks = item.forks,
            language = item.language
        )
    }
}