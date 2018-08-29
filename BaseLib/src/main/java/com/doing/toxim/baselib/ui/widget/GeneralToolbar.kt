package com.doing.toxim.baselib.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import com.doing.toxim.baselib.R
import com.doing.toxim.baselib.utils.DensityUtils
import org.jetbrains.anko.singleLine
import org.jetbrains.anko.textView

class GeneralToolbar constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        Toolbar(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    var mMenuId = R.menu.nullable_toolbar_menu
        private set

    private var mTvTitle: TextView? = null

    init {
        // 初始化自定义属性
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.GeneralToolbar)
        mMenuId = typeArray.getResourceId(R.styleable.GeneralToolbar_menu, R.menu.nullable_toolbar_menu)

        val title = typeArray.getString(R.styleable.GeneralToolbar_commonTitle)
        val titleGravity = typeArray.getInt(R.styleable.GeneralToolbar_titleGravity, Gravity.CENTER)
        val titleSize = typeArray.getDimension(R.styleable.GeneralToolbar_titleSize,
                DensityUtils.sp2px(12.0f).toFloat())
        val titleColor = typeArray.getColorStateList(R.styleable.GeneralToolbar_titleColor)
        if (!TextUtils.isEmpty(title)) {
            initTitleText(title, titleGravity, titleSize, titleColor)
        }

        typeArray.recycle()

        //配置Toolbar
//        this.contentInsetStartWithNavigation = 0
//        this.setContentInsetsRelative(0, 0)
//        this.setContentInsetsAbsolute(0, 0)
//        this.fitsSystemWindows = true
    }

    /**
     * 添加标题栏
     */
    private fun initTitleText(text: String, textGravity: Int, textSize: Float, textColor: ColorStateList) {
        textView {
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.gravity = textGravity
            layoutParams = params

            this.text = text
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            this.setTextColor(textColor)
            this.singleLine = true
            this.maxLines = 1

            val typeface = Typeface.create(this.typeface, Typeface.BOLD)
            setTypeface(typeface)
            this@GeneralToolbar.mTvTitle = this
        }
    }

    fun setGeneralTitle(text: String) {
        mTvTitle?.text = text
    }

}