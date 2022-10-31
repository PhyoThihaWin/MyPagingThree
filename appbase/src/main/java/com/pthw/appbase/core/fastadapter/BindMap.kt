package com.pthw.appbase.core.fastadapter

import android.view.View
import androidx.annotation.LayoutRes

class BindMap<T>(
    @LayoutRes val layout: Int,
    var type: Int = 0,
    val bind: View.(item: T, pos: Int) -> Unit,
    val predicated: (item: T, idx: Int) -> Boolean
) {
    constructor(
        lf: LayoutFactory,
        type: Int = 0,
        bind: View.(item: T, pos: Int) -> Unit,
        predicated: (item: T, idx: Int) -> Boolean
    ) : this(0, type, bind, predicated) {
        layoutFactory = lf
    }

    var layoutFactory: LayoutFactory? = null
}