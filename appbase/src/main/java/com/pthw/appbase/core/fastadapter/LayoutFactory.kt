package com.pthw.appbase.core.fastadapter

import android.view.View
import android.view.ViewGroup

interface LayoutFactory {
    fun createView(parent: ViewGroup, type: Int): View
}