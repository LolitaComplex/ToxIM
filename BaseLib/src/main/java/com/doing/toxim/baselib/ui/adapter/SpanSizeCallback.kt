package com.doing.toxim.baselib.ui.adapter

import android.support.v7.widget.GridLayoutManager

interface SpanSizeCallback {
    fun getSpanSize(layoutManager: GridLayoutManager, oldLookUp: GridLayoutManager.SpanSizeLookup?, position: Int): Int
}