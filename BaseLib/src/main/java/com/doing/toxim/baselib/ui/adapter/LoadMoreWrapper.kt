package com.doing.toxim.baselib.ui.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup

class LoadMoreWrapper<T>(private val mInnerAdapter: BaseAdapter<T>) : BaseAdapter<T>(null, mInnerAdapter.mListData) {

    companion object {
        val ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2

        private val TAG = "LoadMoreWrapper"
    }

    private var mLoadMoreView: View? = null
    private var mLoadMoreLayoutId: Int = 0

    var isFinish: Boolean = false

    private var mOnLoadMoreListener: OnLoadMoreListener? = null

    val innerAdapter: RecyclerView.Adapter<*>
        get() = mInnerAdapter

    private fun hasLoadMore(): Boolean {
        return (mLoadMoreView != null || mLoadMoreLayoutId != 0) && !isFinish
    }

    override val mHeaderCount: Int
        get() = (mInnerAdapter as? HeaderAndFooterWrapper)?.mHeaderCount ?: 0


    /**
     * 这里用mInnerAdapter.getItemCount() - 1 是因为列表滑动较快时，由于RecylclerView加载机制导致
     * 最后一个条目没有走onBindViewHolder，不能触发我们的加载更多。所以为了解决在最后一个条目前一个条目
     * 做判断去触发加载更多，不过也onBindViewHolder中要做过滤机制
     */
    private fun isShowLoadMore(position: Int): Boolean {
        return hasLoadMore() && position >= mInnerAdapter.itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShowLoadMore(position)) {
            ITEM_TYPE_LOAD_MORE
        } else mInnerAdapter.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            val holder: BaseViewHolder
            if (mLoadMoreView != null) {
                holder = BaseViewHolder.createViewHolder(parent.context, mLoadMoreView!!)
            } else {
                holder = BaseViewHolder.createViewHolder(parent.context, parent, mLoadMoreLayoutId)
            }

            return holder
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        Log.w(TAG, "NormalPosition: " + position + "\t ItemCount：" + mInnerAdapter.itemCount)
        if (isShowLoadMore(position)) {
            Log.d(TAG, "Position: " + position + "\t ItemCount：" + mInnerAdapter.itemCount)
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener!!.onLoadMoreRequested(holder)
            }
            return
        }
        mInnerAdapter.onBindViewHolder(holder, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView,
                object : SpanSizeCallback {
                    override fun getSpanSize(layoutManager: GridLayoutManager, oldLookUp: GridLayoutManager.SpanSizeLookup?,
                                             position: Int): Int {
                        return when {
                            isShowLoadMore(position) -> layoutManager.spanCount
                            else -> oldLookUp?.getSpanSize(position) ?: 1
                        }
                    }
                })
    }


    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        mInnerAdapter.onViewAttachedToWindow(holder)

        if (isShowLoadMore(holder.layoutPosition)) {
            setFullSpan(holder)
        }
    }

    private fun setFullSpan(holder: RecyclerView.ViewHolder) {
        val lp = holder.itemView.layoutParams

        if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {

            lp.isFullSpan = true
        }
    }

    override fun getItemCount(): Int {
        return mInnerAdapter.itemCount + if (hasLoadMore()) 1 else 0
    }


    interface OnLoadMoreListener {
        fun onLoadMoreRequested(holder: BaseViewHolder)
    }

    fun setOnLoadMoreListener(loadMoreListener: OnLoadMoreListener?): LoadMoreWrapper<T> {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener
        }
        return this
    }

    fun setLoadMoreView(loadMoreView: View): LoadMoreWrapper<T> {
        mLoadMoreView = loadMoreView
        return this
    }

    fun setLoadMoreView(layoutId: Int): LoadMoreWrapper<T> {
        mLoadMoreLayoutId = layoutId
        return this
    }
}
