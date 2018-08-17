package com.doing.toxim.baselib.ui.adapter

class DataManager<in T>(private val mAdapter: BaseAdapter<T>, private val mListData: MutableList<T>){

    fun addItem(data: T, headerCount: Int) {
        addItem(mListData.size, data, headerCount)
    }

    fun addItem(position: Int, data: T, headerCount: Int) {
        mListData.add(position, data)
        mAdapter.notifyItemInserted(position + headerCount)
    }

    fun addItems(data: List<T>, headerCount: Int) {
        addItems(mListData.size, data, headerCount)
    }

    fun addItems(position: Int, data: List<T>, headerCount: Int) {
        mListData.addAll(position, data)
        mAdapter.notifyItemRangeInserted(headerCount + position, data.size)
    }


    fun setItem(position: Int, data: T) {
        mListData[position] = data
        mAdapter.notifyItemChanged(position)
    }

    fun setItems(data: List<T>) {
        mListData.clear()
        mListData.addAll(data)
        mAdapter.notifyDataSetChanged()
    }

    fun moveItem(startPosition: Int, endPosition: Int, headerCount: Int) {
        mAdapter.notifyItemMoved(startPosition + headerCount, endPosition + headerCount)
    }

    fun deleteItem(position: Int, headerCount: Int) {
        mListData.removeAt(position)
        mAdapter.notifyItemRemoved(position + headerCount)
    }

    fun deleteItem(item: T, headerCount: Int): Int {
        val position = mListData.indexOf(item)
        deleteItem(position, headerCount)
        return position
    }

    fun deleteItems(removeList: List<T>, headerCount: Int) {
        for (item in removeList) {
            deleteItem(item, headerCount)
        }
    }

}