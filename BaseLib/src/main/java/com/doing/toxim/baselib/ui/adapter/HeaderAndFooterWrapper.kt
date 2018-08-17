package com.doing.toxim.baselib.ui.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by Doing on 2016/9/19.
 *
 */
class HeaderAndFooterWrapper<T>(private val mInterAdapter: BaseAdapter<T>) : BaseAdapter<T>(null, mInterAdapter.mListData) {

    private val mHeaderViews = SparseArrayCompat<View>()
    private val mFooterViews = SparseArrayCompat<View>()

    companion object {
        private val BASE_ITEM_TYPE_HEADER = 100000
        private val BASE_ITEM_TYPE_FOOTER = 200000
    }

    override val mHeaderCount: Int
        get() = mHeaderViews.size()

    private val mRealItemCount: Int
        get() = mInterAdapter.itemCount

    val mFooterCount: Int
        get() = mFooterViews.size()

    override fun getItemViewType(position: Int): Int {
        if (isHeaderViewPosition(position)) {
            return mHeaderViews.keyAt(position)
        } else if (isFooterViewPosition(position)) {
            return mFooterViews.keyAt(position - mRealItemCount - mHeaderCount)
        }

        return mInterAdapter.getItemViewType(position - mHeaderCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (mHeaderViews.get(viewType) != null) {
            return BaseViewHolder.createViewHolder(parent.context, mHeaderViews.get(viewType))
        }

        return if (mFooterViews.get(viewType) != null) {
            BaseViewHolder.createViewHolder(parent.context, mFooterViews.get(viewType))
        } else mInterAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (isHeaderViewPosition(position)) {
            return
        }
        if (isFooterViewPosition(position)) {
            return
        }

        mInterAdapter.onBindViewHolder(holder, position - mHeaderCount)
    }

    override fun getItemCount(): Int {
        return mHeaderCount + mRealItemCount + mFooterCount
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInterAdapter, recyclerView, object : SpanSizeCallback {
            override fun getSpanSize(layoutManager: GridLayoutManager, oldLookUp: GridLayoutManager.SpanSizeLookup?
                                     , position: Int): Int {
                val viewType = getItemViewType(position)
                return when {
                    mHeaderViews.get(viewType) != null -> layoutManager.spanCount
                    mFooterViews.get(viewType) != null -> layoutManager.spanCount
                    else -> oldLookUp?.getSpanSize(position) ?: 1
                }
            }
        })
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        mInterAdapter.onViewAttachedToWindow(holder)

        val position = holder.layoutPosition
        if (isHeaderViewPosition(position) || isFooterViewPosition(position)) {
            WrapperUtils.setFullSpan(holder)
        }
    }

    private fun isHeaderViewPosition(position: Int): Boolean {
        return position < mHeaderCount
    }

    private fun isFooterViewPosition(position: Int): Boolean {
        return position >= mRealItemCount + mHeaderCount
    }

    fun addHeaderView(view: View) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view)
    }

    fun addFooterView(view: View) {
        mFooterViews.put(mFooterViews.size() + BASE_ITEM_TYPE_FOOTER, view)
    }

}
