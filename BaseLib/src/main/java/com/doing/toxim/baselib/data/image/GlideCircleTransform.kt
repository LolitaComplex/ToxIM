package com.doing.toxim.baselib.data.image

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

class GlideCircleTransform : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        val width = toTransform.width
        val height = toTransform.height
        val size = Math.min(width, height)

        var result: Bitmap? = pool.get(width, height, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        canvas.drawCircle((size / 2).toFloat(), (size / 2).toFloat(), (size / 2).toFloat(), paint)

        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }
}
