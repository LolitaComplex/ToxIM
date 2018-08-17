package com.doing.toxim.baselib.data.image

import android.os.Environment
import android.os.StatFs

import java.io.File

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */

object DiskUtils {

    val sdAvialableSize: Long
        get() {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val availableBlocks = stat.availableBlocks
            val blockSize = stat.blockSize
            return (availableBlocks * blockSize).toLong()
        }
}
