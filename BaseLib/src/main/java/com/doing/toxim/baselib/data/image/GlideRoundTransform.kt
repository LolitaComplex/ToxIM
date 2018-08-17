package com.doing.toxim.baselib.data.image

import android.content.res.Resources
import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */

class GlideRoundTransform(radiusDp: Float) : BitmapTransformation() {

    private val mRadius: Float = Resources.getSystem().displayMetrics.density * radiusDp

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        var result: Bitmap? = pool.get(toTransform.width, toTransform.height,
                Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(toTransform.width, toTransform.height,
                    Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val rectF = RectF(0f, 0f, toTransform.width.toFloat(), toTransform.height.toFloat())
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint)
        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }
}
