package com.doing.toxim.baselib.ui.dialog

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.design.widget.BottomSheetDialog
import android.view.View

abstract class BaseBottomDialog(val mContext: Context){

    private val mDialog = BottomSheetDialog(mContext)

    private var mFirstStart = true

    fun show() {
        if (mFirstStart) {
            mDialog.setContentView(getLayoutId())
            initView(mDialog)
            mDialog.delegate.findViewById<View>(android.support.design.R.id.design_bottom_sheet)
                    ?.setBackgroundResource(android.R.color.transparent)
            initDialogAttribute(mDialog)
        }
        if (!mDialog.isShowing) {
            mDialog.show()
        }
        mFirstStart = false
    }

    fun dismiss() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView(dialog: BottomSheetDialog)

    protected open fun initDialogAttribute(dialog: BottomSheetDialog) {

    }

}
