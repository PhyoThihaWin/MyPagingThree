package com.pthw.appbase.exception

interface UnidirectionalMap<F, T> {
    fun map(item: F): T
}