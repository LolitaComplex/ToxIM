package com.doing.toxim.baselib.utils

import android.widget.Toast
import com.doing.toxim.baselib.common.BaseApplication

fun String.toast() {
    ToastUtil.show(this)
}

class ToastUtil{
    companion object {
        private var mToast: Toast? = null

        fun show(text: String) {
            mToast?.let {
                it.setText(text)
                it.show()
            }

            if (mToast == null) {
                mToast = Toast.makeText(UiUtils.getContext(), text, Toast.LENGTH_SHORT)
                mToast!!.show()
            }
        }
    }
}