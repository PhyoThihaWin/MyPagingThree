package com.pthw.cache.config

import android.content.Context
import androidx.core.content.edit
import com.pthw.data.config.LanguageCacheDataSource
import com.pthw.data.model.Language
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LanguageCacheDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) :
    LanguageCacheDataSource {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "pref.user"
        private const val PREF_KEY_LANGUAGE = "language.key"
    }

    override suspend fun getLanguage(): Language {
        val languageCode =
            sharedPreferences.getString(PREF_KEY_LANGUAGE, Language.ENGLISH.languageCode)
        return Language.getLanguageFromCode(languageCode!!)
    }


    override fun getLanguageNormal(): Language {
        val languageCode =
            sharedPreferences.getString(PREF_KEY_LANGUAGE, Language.ENGLISH.languageCode)
        return Language.getLanguageFromCode(languageCode!!)
    }

    override suspend fun putLanguage(language: Language) {
        sharedPreferences.edit {
            putString(PREF_KEY_LANGUAGE, language.languageCode)
        }
    }

    override suspend fun clearLanguage() {
        sharedPreferences.edit {
            remove(PREF_KEY_LANGUAGE)
        }
    }

}
