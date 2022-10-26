package com.pthw.data.config

interface BaseUrlProvider {
    fun updateBaseUrl(baseUrl: String)
    fun getBaseUrl(): String
}