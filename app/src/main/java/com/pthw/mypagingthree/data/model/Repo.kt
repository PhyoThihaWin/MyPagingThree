package com.pthw.mypagingthree.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


/**
 * Immutable model class for a Github repo that holds all the information about a repository.
 * Objects of this type are received from the Github API, therefore all the fields are annotated
 * with the serialized name.
 * This class also defines the Room repos table, where the repo [id] is the primary key.
 */
@Entity(tableName = "repos")
data class Repo(
    @PrimaryKey @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "full_name") val fullName: String,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "html_url") val url: String,
    @field:Json(name = "stargazers_count") val stars: Int,
    @field:Json(name = "forks_count") val forks: Int,
    @field:Json(name = "language") val language: String?
)
