package com.pthw.data.config

import com.pthw.data.model.Language

interface LanguageCacheDataSource {

    fun getLanguageNormal(): Language

    suspend fun getLanguage(): Language

    suspend fun putLanguage(language: Language)

    suspend fun clearLanguage()
}