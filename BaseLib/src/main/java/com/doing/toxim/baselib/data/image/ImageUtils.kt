package com.doing.toxim.baselib.data.image

import android.content.Context
import android.graphics.Bitmap
import android.util.SparseArray
import android.widget.ImageView
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskCache
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemoryCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import java.io.File


/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */

object ImageUtils {

    private var sMemoryCache: MemoryCache? = null
    private var sBitmapPoolCache: LruBitmapPool? = null
    private var sGlideCircleTransform: GlideCircleTransform? = null
    private val sRoundTransforms = SparseArray<GlideRoundTransform>()

    /*package*/
    internal fun getMemoryCache(context: Context): MemoryCache? {
        if (sMemoryCache == null) {
            synchronized(ImageUtils::class.java) {
                if (sMemoryCache == null) {
                    val calculator = MemorySizeCalculator.Builder(context).build()
                    sMemoryCache = LruResourceCache(calculator.memoryCacheSize.toLong())
                }
            }
        }
        return sMemoryCache
    }

    /*package*/
    internal fun getBitmapPoolCache(context: Context): LruBitmapPool? {
        if (sBitmapPoolCache == null) {
            synchronized(ImageUtils::class.java) {
                if (sBitmapPoolCache == null) {
                    val calculator = MemorySizeCalculator.Builder(context).build()
                    sBitmapPoolCache = LruBitmapPool(calculator.memoryCacheSize.toLong())
                }
            }
        }
        return sBitmapPoolCache
    }

    private fun getCircleBitmapTransform(): BitmapTransformation? {
        if (sGlideCircleTransform == null) {
            synchronized(ImageUtils::class.java) {
                if (sGlideCircleTransform == null) {
                    sGlideCircleTransform = GlideCircleTransform()
                }
            }
        }
        return sGlideCircleTransform
    }

    private fun getRoundBitmapTransform(raduisDp: Int): BitmapTransformation? {
        var transformation: GlideRoundTransform? = sRoundTransforms.get(raduisDp)

        if (transformation == null) {
            synchronized(ImageUtils::class.java) {
                transformation = sRoundTransforms.get(raduisDp)
                if (transformation == null) {
                    transformation = GlideRoundTransform(raduisDp.toFloat())
                    sRoundTransforms.put(raduisDp, transformation)
                }
            }
        }
        return transformation
    }

    fun setUrl(imageView: ImageView, url: String) {
        setUrl(imageView, url, 0, 0, null, 0)
    }

    fun setUrl(imageView: ImageView, url: String, placeHolder: Int) {
        setUrl(imageView, url, 0, 0, null, placeHolder)
    }

    fun setUrl(imageView: ImageView, url: String, width: Int, height: Int) {
        setUrl(imageView, url, width, height, null, 0)
    }

    fun setRadiusUrl(imageView: ImageView, url: String, radiusDp: Int) {
        val transformation = getRoundBitmapTransform(radiusDp)
        setUrl(imageView, url, 0, 0, transformation, 0)
    }

    fun setRadiusUrl(imageView: ImageView, url: String, width: Int, height: Int, radiusDp: Int) {
        val transformation = getRoundBitmapTransform(radiusDp)
        setUrl(imageView, url, width, height, transformation, 0)
    }

    fun setCircleUrl(imageView: ImageView, url: String) {
        val transformation = CircleCrop()
        setUrl(imageView, url, 0, 0, transformation, 0)
    }

    fun setCircleUrl(imageView: ImageView, url: String, placeholderImg: Int) {
        val transformation = CircleCrop()
        setUrl(imageView, url, 0, 0, transformation, placeholderImg)
    }

    fun setCircleUrl(imageView: ImageView, url: String, width: Int, height: Int) {
        val transformation = CircleCrop()
        setUrl(imageView, url, width, height, transformation, 0)
    }

    private fun setUrl(imageView: ImageView, url: String, width: Int, height: Int, transformation: BitmapTransformation?, placeholderImg: Int) {
        var sizeUrl = if (width > 0 && height > 0)
            UrlGenerator.getImageUrl(url, width, height)
        else
            url

        if (!sizeUrl.contains("http:")) {
            sizeUrl = "file://$sizeUrl"
        }

        val options = RequestOptions()
        if (placeholderImg != 0) {
            options.placeholder(placeholderImg).error(placeholderImg)
        }

        val request = GlideApp.with(imageView)
                .load(sizeUrl)
                .transition(DrawableTransitionOptions.withCrossFade())


        if (transformation != null) {
            options.transform(transformation)
        }

        request.apply(options).into(imageView)
    }

    fun setResId(imageView: ImageView, resId: Int) {
        setResId(imageView, resId, null)
    }

    fun setResId(imageView: ImageView, resId: Int, radiusDp: Int) {
        setResId(imageView, resId, getRoundBitmapTransform(radiusDp))
    }

    fun setCircleResId(imageView: ImageView, resId: Int) {
        setResId(imageView, resId, CenterCrop())
    }

    private fun setResId(imageView: ImageView, resId: Int, transformation: BitmapTransformation?) {
        val request = GlideApp.with(imageView.context)
                .load(resId)
                .transition(DrawableTransitionOptions.withCrossFade())

        if (transformation != null) {
            request.apply(RequestOptions().transform(transformation))
        }

        request.into(imageView)
    }

    fun setFilePath(imageView: ImageView, filePath: String) {
        setFilePath(imageView, filePath, null)
    }

    fun setFilePath(imageView: ImageView, filePath: String, radius: Int) {
        setFilePath(imageView, filePath, getRoundBitmapTransform(radius))
    }

    fun setCircleFilePath(imageView: ImageView, filePath: String) {
        setFilePath(imageView, filePath, CenterCrop())
    }

    private fun setFilePath(imageView: ImageView, filepath: String, transformation: Transformation<Bitmap>?) {
        val builder = GlideApp.with(imageView.context)
                .load(filepath)
                .transition(DrawableTransitionOptions.withCrossFade())

        if (transformation != null) {
            builder.apply(RequestOptions().transform(transformation))
        }

        builder.into(imageView)
    }

    //其他
    fun getDiskCacheSize(context: Context): Long {
        val diskCacheDir = getDiskCacheDir(context)
        return getFolderSize(diskCacheDir)
    }

    fun clearDiskCache(context: Context) {
        GlideApp.get(context).clearDiskCache()
    }

    fun clearMemoryCache(context: Context) {
        GlideApp.get(context).clearMemory()
    }

    fun prefetch(context: Context, url: String, width: Int, height: Int) {
        val sizeUrl = if (width > 0 && height > 0)
            UrlGenerator.getImageUrl(url, width, height)
        else
            url

        GlideApp.with(context).load(sizeUrl).submit(width, height)
    }

    private fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            if (file.isDirectory) {
                val files = file.listFiles()
                for (subFile in files) {
                    if (subFile.isDirectory) {
                        size += getFolderSize(subFile)
                    } else {
                        size += subFile.length()
                    }
                }
            } else {
                size += file.length()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return size
    }

    /**
     * 返回Glide本地磁盘缓存路径
     *
     * @param context
     * @return
     */
    private fun getDiskCacheDir(context: Context): File {
        val cacheDirectory = context.cacheDir
        return File(cacheDirectory, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR)
    }
}
