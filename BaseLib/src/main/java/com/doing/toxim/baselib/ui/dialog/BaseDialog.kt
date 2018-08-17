package com.doing.toxim.baselib.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.*
import com.doing.toxim.baselib.R

abstract class BaseDialog : DialogFragment(){

    var mOnKeyListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.apply {
            initDialogAttribute(this)
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.LightDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiView(view)
    }


    protected open fun initDialogAttribute(dialog: Dialog) {
        val window = dialog.window
        val attributes = window.attributes

        attributes.softInputMode =
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        dialog.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                mOnKeyListener?.invoke()
            }
            false
        }


//        attributes.alpha = 0.5f
        // 灰暗程度
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        attributes.dimAmount = 0f

        isCancelable = true
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        attributes.gravity = Gravity.CENTER

        dialog.window.attributes = attributes
    }

    open fun getOutTag(): String {
        return this.javaClass.simpleName + " : " + dialog?.hashCode()
    }

    open fun show(manager: FragmentManager?) {
//        if (!isVisible) {
            this.show(manager, getOutTag())
//        }
    }

    // 模板方法
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun intiView(container: View)
}
