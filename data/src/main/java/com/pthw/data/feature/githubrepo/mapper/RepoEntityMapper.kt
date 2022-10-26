package com.pthw.data.feature.githubrepo.mapper

import com.pthw.data.feature.githubrepo.entity.RepoEntity
import com.pthw.domain.feature.githubrepo.model.Repo
import com.pthw.domain.mapper.UnidirectionalMap
import javax.inject.Inject

class RepoEntityMapper @Inject constructor() :
    UnidirectionalMap<RepoEntity, Repo> {
    override fun map(item: RepoEntity): Repo {
        return Repo(
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