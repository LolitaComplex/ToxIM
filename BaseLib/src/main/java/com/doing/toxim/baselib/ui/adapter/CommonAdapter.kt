package com.doing.toxim.baselib.ui.adapter

import android.content.Context
import android.view.LayoutInflater

/**
 * Created by Doing on 2018/4/4.
 *
 */
abstract class CommonAdapter<T>(context: Context, protected var mLayoutId: Int, data: MutableList<T>) : MultiItemTypeAdapter<T>(context, data) {
    protected val mInflater: LayoutInflater = LayoutInflater.from(context)
    var isEnable = true

    init {

        addItemViewDelegate(object : ItemViewDelegate<T> {
            override val mItemViewLayoutId: Int
                get() = mLayoutId

            override val mIsEnable: Boolean
                get() = this@CommonAdapter.isEnable

            override fun isForViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun convert(holder: BaseViewHolder, t: T, position: Int) {
                this@CommonAdapter.convert(holder, t, position)
            }
        })

    }

    abstract override fun convert(holder: BaseViewHolder, t: T, position: Int)
}
