package com.pthw.cache.feature.githubrepo.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pthw.cache.feature.githubrepo.entity.RepoDbEntity

@Dao
interface RepoSourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repoSources: List<RepoDbEntity>)

    @Query(
        "SELECT * FROM repos WHERE " +
                "name LIKE :queryString OR description LIKE :queryString " +
                "ORDER BY stars DESC, name ASC"
    )
    fun reposByName(queryString: String): PagingSource<Int, RepoDbEntity>

    @Query("DELETE FROM repos")
    suspend fun clearRepos()
}