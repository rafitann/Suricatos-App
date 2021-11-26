package com.sofie.widget.camera.core.view

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

class CameraView: RelativeLayout {

    private lateinit var cameraView: View

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) { initalize() }
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { initalize() }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int,
        defStyleRes: Int
    ):super(context, attrs, defStyleAttr, defStyleRes) { initalize() }

    private fun initalize() {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )

        cameraView = ((if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            NewCameraView(context) else OldCameraView(context)) as View).also {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        addView(cameraView)
    }

    fun onResume() {
        (cameraView as Device).onResume()
    }

    fun onStop() {
        (cameraView as Device).onStop()
    }


    fun captureImage(bitmap: (picture: Bitmap) -> Unit) {
        (cameraView as Device).captureBitmap(bitmap)
    }

}