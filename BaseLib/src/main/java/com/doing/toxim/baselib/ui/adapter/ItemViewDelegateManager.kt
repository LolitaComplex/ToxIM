package com.doing.toxim.baselib.ui.adapter

import android.support.v4.util.SparseArrayCompat

/**
 * Created by Doing on 2018/4/4.
 *
 */
class ItemViewDelegateManager<T> {

    private val mDelegates = SparseArrayCompat<ItemViewDelegate<T>>()


    val itemViewDelegateCount: Int
        get() = mDelegates.size()

    fun addDelegate(delegate: ItemViewDelegate<T>?): ItemViewDelegateManager<T> {
        val viewType = mDelegates.size()
        if (delegate != null) {
            mDelegates.put(viewType, delegate)
        }

        return this
    }

    fun addDelegate(viewType: Int, delegate: ItemViewDelegate<T>): ItemViewDelegateManager<T> {
        if (mDelegates.get(viewType) != null) {
            throw IllegalArgumentException("An ItemViewDelegate is already registered for the viewType = "
                    + viewType + ". It's" + mDelegates.get(viewType))
        }
        mDelegates.put(viewType, delegate)
        return this
    }

    fun removeDelegate(delegate: ItemViewDelegate<T>?): ItemViewDelegateManager<T> {
        if (delegate == null) {
            throw NullPointerException("ItemViewDelegate is null")
        }

        val typePosition = mDelegates.indexOfValue(delegate)
        if (typePosition >= 0) {
            mDelegates.remove(typePosition)
        }
        return this
    }

    fun removeDelegate(itemType: Int): ItemViewDelegateManager<T> {
        val indexToRemove = mDelegates.indexOfKey(itemType)
        if (indexToRemove >= 0) {
            mDelegates.removeAt(indexToRemove)
        }
        return this
    }

    fun getItemViewDelegate(viewType: Int): ItemViewDelegate<T> {
        return mDelegates.get(viewType)
    }

    fun getItemViewType(delegate: ItemViewDelegate<T>?): Int {
        if (delegate == null) {
            throw NullPointerException("ItemViewDelegate is null")
        }

        return mDelegates.indexOfValue(delegate)
    }

    fun getItemViewType(item: T, position: Int): Int {
        val delegatesCount = mDelegates.size()

        for (i in 0 until delegatesCount) {
            val itemViewDelegate = mDelegates.valueAt(i)
            if (itemViewDelegate.isForViewType(item, position)) {
                return mDelegates.keyAt(i)
            }
        }
        throw IllegalArgumentException("No ItemViewDelegate added that matches position = "
                + position + "in data source")
    }


    fun convert(holder: BaseViewHolder, item: T, position: Int) {
        val delegatesCount = mDelegates.size()
        for (i in 0 until delegatesCount) {
            val itemViewDelegate = mDelegates.valueAt(i)
            if (itemViewDelegate.isForViewType(item, position)) {
                itemViewDelegate.convert(holder, item, position)
                return
            }
        }

        throw IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=$position in data source")
    }

}
