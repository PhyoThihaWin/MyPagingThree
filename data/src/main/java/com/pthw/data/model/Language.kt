package com.pthw.data.model

enum class Language(val languageCode: String) {
    MYANMAR_UNICODE("mm"),
    ENGLISH("en");
    companion object {
        private const val LANGUAGE_CODE_UNICODE = "mm"
        private const val LANGUAGE_CODE_ENGLISH = "en"
        fun getLanguageFromCode(code: String): Language {
            return when (code) {
                LANGUAGE_CODE_UNICODE -> MYANMAR_UNICODE
                LANGUAGE_CODE_ENGLISH -> ENGLISH
                else -> throw IllegalArgumentException()
            }
        }
    }
}