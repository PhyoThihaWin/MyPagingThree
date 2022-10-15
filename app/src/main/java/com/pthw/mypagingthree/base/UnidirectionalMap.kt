package com.pthw.mypagingthree.base

interface UnidirectionalMap<F, T> {
    fun map(item: F): T
}