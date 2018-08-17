package com.doing.toxim.baselib.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import com.doing.toxim.baselib.data.image.ImageUtils


/**
 * Created by Doing on 2018/4/4.
 */
class BaseViewHolder private constructor(private val mContext: Context, private var contentView: View) : RecyclerView.ViewHolder(contentView) {

    private val mViews: SparseArrayCompat<View> = SparseArrayCompat()

    companion object {

        fun createViewHolder(context: Context, itemView: View): BaseViewHolder {
            return BaseViewHolder(context, itemView)
        }

        fun createViewHolder(context: Context, parent: ViewGroup, layoutId: Int): BaseViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return BaseViewHolder(context, itemView)
        }
    }

    private inline fun <reified T : View> getView(viewId: Int): T? {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = contentView.findViewById(viewId)
            view ?: mViews.put(viewId, view)
        }
        return view as T?
    }

    fun setText(viewId: Int, text: String): BaseViewHolder {
        val textView = getView<TextView>(viewId)
        textView?.text = text
        return this
    }

    fun setTextColor(viewId: Int, color: Int): BaseViewHolder {
        val textView = getView<TextView>(viewId)
        textView?.setTextColor(color)
        return this
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun setBackground(viewId: Int, drawable: Drawable): BaseViewHolder {
        getView<View>(viewId)?.background = drawable
        return this
    }


    fun setBackground(viewId: Int, resource: Int): BaseViewHolder {
        getView<View>(viewId)?.setBackgroundResource(resource)
        return this
    }

    fun setBackgroundRes(viewId: Int, resId: Int): BaseViewHolder {
        getView<View>(viewId)?.setBackgroundResource(resId)
        return this
    }

    fun setTextColorRes(viewId: Int, res: Int): BaseViewHolder {
        val textView = getView<TextView>(viewId)
        textView?.setTextColor(ContextCompat.getColor(mContext, res))
        return this
    }

    fun setChecked(viewId: Int, isChecked: Boolean): BaseViewHolder {
        val cb = getView<CompoundButton>(viewId)
        cb?.isChecked = isChecked
        return this
    }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap): BaseViewHolder {
        val imageView = getView<ImageView>(viewId)
        imageView?.setImageBitmap(bitmap)
        return this
    }

    fun setImageBitmapRes(viewId: Int, bitmapRes: Int): BaseViewHolder {
        val imageView = getView<ImageView>(viewId)
        imageView?.setImageResource(bitmapRes)
        return this
    }

    fun setImageDrawable(viewId: Int, drawable: Drawable): BaseViewHolder {
        val imageView = getView<ImageView>(viewId)
        imageView?.setImageDrawable(drawable)
        return this
    }

    fun setImageUrl(viewId: Int, url: String, placeHolder: Int): BaseViewHolder {
        if (TextUtils.isEmpty(url)) {
            return this
        }

        val imageView = getView<ImageView>(viewId)
        if (imageView != null) {
            ImageUtils.setUrl(imageView, url, placeHolder)
        }
        return this
    }

    fun setImageRoundUrl(viewId: Int, url: String, placeHolder: Int): BaseViewHolder {
        if (TextUtils.isEmpty(url)) {
            return this
        }

        val imageView = getView<ImageView>(viewId)
        if (imageView != null) {
            ImageUtils.setCircleUrl(imageView, url, placeHolder)
        }
        return this
    }

    fun setVisible(viewId: Int, boo: Boolean): BaseViewHolder {
        val view = getView<View>(viewId)
        view?.visibility = if (boo) View.VISIBLE else View.GONE
        return this
    }

    fun setVisible(viewId: Int, visible: Int): BaseViewHolder {
        val view = getView<View>(viewId)
        view?.visibility = visible
        return this
    }

    fun setOnClickListener(viewId: Int, clickListener: View.OnClickListener): BaseViewHolder {
        val view = getView<View>(viewId)
        view?.setOnClickListener(clickListener)
        return this
    }

    fun setTag(viewId: Int, tag: Any): BaseViewHolder {
        val view = getView<View>(viewId)
        view?.tag = tag
        return this
    }

    fun setOnTouchListener(viewId: Int, touchListener: View.OnTouchListener): BaseViewHolder {
        val view = getView<View>(viewId)
        setViewClickable(view)
        view?.setOnTouchListener(touchListener)
        return this
    }

    fun setOnLongClickListener(viewId: Int, longClickListener: View.OnLongClickListener): BaseViewHolder {
        val view = getView<View>(viewId)
        setViewClickable(view)
        view?.setOnLongClickListener(longClickListener)
        return this
    }

    private fun setViewClickable(view: View?) {
        view?.isClickable = true
        view?.isLongClickable = true
    }


    fun setOnCheckChangeListener(viewId: Int, onCheckedChangeListener: CompoundButton.OnCheckedChangeListener): BaseViewHolder {
        val view = getView<CompoundButton>(viewId)
        view?.setOnCheckedChangeListener(onCheckedChangeListener)
        return this
    }
}
