package com.doing.toxim.baselib.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import kotlin.collections.ArrayList

abstract class BaseAdapter<T>(protected var mContext: Context?, data: MutableList<T>?) : RecyclerView.Adapter<BaseViewHolder>() {
    val mListData: MutableList<T> = data?: ArrayList()
    private val mDataManager by lazy { DataManager(this, mListData) }

    open val mHeaderCount
        get() = 0

    fun addItem(data: T) {
        mDataManager.addItem(data, this.mHeaderCount)
    }

    fun addItem(position: Int, data: T) {
        mDataManager.addItem(position, data, this.mHeaderCount)
    }

    fun addItems(data: MutableList<T>) {
        mDataManager.addItems(data, this.mHeaderCount)
    }

    fun addItems(position: Int, data: MutableList<T>) {
        mDataManager.addItems(position, data, this.mHeaderCount)
    }

    fun setItem(position: Int, data: T) {
        mDataManager.setItem(position, data)
    }

    fun setItems(data: MutableList<T>) {
        mDataManager.setItems(data)
    }

    fun moveItem(startPosition: Int, endPosition: Int) {
        mDataManager.moveItem(startPosition, endPosition, this.mHeaderCount)
    }

    fun deleteItem(position: Int) {
        mDataManager.deleteItem(position, this.mHeaderCount)
    }

    fun deleteItem(item: T): Int {
        return mDataManager.deleteItem(item, this.mHeaderCount)
    }

    fun deleteItems(removeList: MutableList<T>) {
        for (item in removeList) {
            deleteItem(item)
        }
    }
}