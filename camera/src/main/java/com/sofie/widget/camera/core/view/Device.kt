package com.sofie.widget.camera.core.view

import android.graphics.Bitmap

internal interface Device {

    fun onResume()
    fun onStop()

    fun captureBitmap(picture: (bitmap: Bitmap) -> Unit)
//    fun captureFile(picture: (file: File) -> Unit)
}