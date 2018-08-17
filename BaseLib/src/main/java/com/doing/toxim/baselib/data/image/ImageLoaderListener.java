package com.doing.toxim.baselib.data.image;

import java.io.File;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-13.
 */
public interface ImageLoaderListener {

    void onLoadStart();

    void onLoadSuccess(File resource);

    void onLoadFailed();
}
