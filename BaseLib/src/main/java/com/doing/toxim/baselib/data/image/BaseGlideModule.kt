package com.doing.toxim.baselib.data.image

import android.content.Context

import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */
@GlideModule
class BaseGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
//        val availableSize = DiskUtils.sdAvialableSize
        val availableSize = 1024 * 1024 * 200
        val diskCacheSize = availableSize / 2
        val memoryCacheSize = Runtime.getRuntime().maxMemory() / 8
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSize.toLong()))
                .setMemoryCache(LruResourceCache(memoryCacheSize))
                .setBitmapPool(LruBitmapPool(memoryCacheSize / 2))
    }
}
