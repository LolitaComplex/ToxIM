package com.doing.toxim.baselib.ui.adapter

import android.support.annotation.IntDef


/**
 * Created by Doing on 2018/4/10.
 *
 */
abstract class SimpleOnLoadMoreListener<T> protected constructor(private val mLoadMoreAdapter: LoadMoreWrapper<T>,
                      private val mPageCount: Int) : LoadMoreWrapper.OnLoadMoreListener {

    companion object {
        private const val TYPE_IDLE = 1000
        private const val TYPE_LOADING = 1001
        private const val TYPE_NO_MORE_DATA = 1002
        private const val TYPE_REFRESHING = 1003
    }
    @IntDef(TYPE_IDLE, TYPE_LOADING, TYPE_NO_MORE_DATA, TYPE_REFRESHING)
    @Retention(AnnotationRetention.SOURCE)
    private annotation class LoadType


    @LoadType
    private var mLoadType = TYPE_IDLE

    private var mCurrentHolder: BaseViewHolder? = null

    override fun onLoadMoreRequested(holder: BaseViewHolder) {
        mCurrentHolder = holder
        val itemCount = mLoadMoreAdapter.mListData.count()
        if (itemCount == 0) {
            return
        }

        when (mLoadType) {
            TYPE_IDLE -> if (itemCount % mPageCount == 0) {
                mLoadType = TYPE_LOADING
                onLoadMore(holder)
            }
            TYPE_LOADING, TYPE_NO_MORE_DATA, TYPE_REFRESHING -> { }
        }
    }

    private fun setTypeIdle() {
        mLoadType = TYPE_IDLE
    }

    fun onLoadMoreSuccess(data: MutableList<T>?) {
        setTypeIdle()
        if (data == null || data.size < mPageCount && mCurrentHolder != null) {
            mLoadType = TYPE_NO_MORE_DATA
            mLoadMoreAdapter.notifyItemRemoved(mLoadMoreAdapter.itemCount)
            mLoadMoreAdapter.isFinish = true
        }

        if (data != null) {
            mLoadMoreAdapter.addItems(data)
        }
    }

    fun onRefreshSuccess(data: MutableList<T>) {
        setTypeIdle()
        mLoadMoreAdapter.isFinish = false
        if (data.size < mPageCount) {
            mLoadType = TYPE_NO_MORE_DATA
            mLoadMoreAdapter.isFinish = true
        }
        mLoadMoreAdapter.setItems(data)
    }

    fun onLoadMoreError() {
        setTypeIdle()
    }


    fun onRefreshing() {
        mLoadType = TYPE_REFRESHING
    }

    // ======= 模板方法 ==========
    protected abstract fun onLoadNoMoreData(holder: BaseViewHolder)

    protected abstract fun onLoadMore(holder: BaseViewHolder)

}
