package com.doing.toxim.baselib.ui.adapter

/**
 * Created by Doing on 2018/4/4.
 *
 */
interface ItemViewDelegate<in T> {

    val mItemViewLayoutId: Int

    val mIsEnable: Boolean

    fun isForViewType(item: T, position: Int): Boolean

    fun convert(holder: BaseViewHolder, t: T, position: Int)
}
