package suricatos.core

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.Camera
import android.os.Build
import android.util.AttributeSet
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager
import android.widget.RelativeLayout
import kotlin.math.ceil

class OldCameraView: RelativeLayout, Device, SurfaceHolder.Callback, Camera.PreviewCallback {

    private lateinit var holder: SurfaceHolder
    private var camera: Camera? = null
    private var isPreviewCamera = false
    private var previewBuffer: ByteArray? = null

    private var surfaceView = SurfaceView(context).also {
        it.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

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
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        holder = surfaceView.holder
        holder.addCallback(this)
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)

        addView(surfaceView)
    }

    ///// SurfaceHolder.Callback
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        setupCameraParameters(camera)
        camera?.startPreview()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopCamera()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            startCamera(holder)
        } catch (e: Exception) {

        }
    }

    ///// Camera.PreviewCallback
    override fun onPreviewFrame(buffer: ByteArray?, camera: Camera?) {
        camera?.addCallbackBuffer(buffer)
    }


    override fun onResume() {}
    override fun onStop() {}

    override fun captureBitmap(picture: (bitmap: Bitmap) -> Unit) {
        stopCamera()
        picture.invoke(BitmapFactory.decodeByteArray(previewBuffer, 0, previewBuffer!!.size))
    }

    ////// Private
    private fun startCamera(holder: SurfaceHolder) {
        if (isPreviewCamera)
            stopCamera()

        camera = Camera.open().also {
            it.setPreviewDisplay(holder)
            it.addCallbackBuffer(previewBuffer)
            it.setPreviewCallbackWithBuffer(this)
        }

        setupCameraParameters(camera)?.let {
            previewBuffer = ByteArray(calculateBufferSize(it.width, it.height))
            holder.setFixedSize(it.width, it.height)
        }

        camera?.startPreview()
        isPreviewCamera = true
    }

    private fun stopCamera() {
        camera?.let {
            it.setPreviewCallback(null)
            it.stopPreview()
            it.release()
        }

        camera = null
        isPreviewCamera = false
    }


    private fun setupCameraParameters(camera: Camera?): Camera.Size? {
        if (camera == null) return null

        val parameters = camera.parameters

        val previewSizes = parameters.supportedPreviewSizes
        val previewSize = previewSizes.last()

        parameters.previewFormat = ImageFormat.YV12
        parameters.flashMode = Camera.Parameters.FLASH_MODE_AUTO
        parameters.setPreviewSize(previewSize.width, previewSize.height)
        parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE

//        camera.parameters = parameters

        val display =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay

        when (display.rotation) {
            Surface.ROTATION_0 -> camera.setDisplayOrientation(90)
            Surface.ROTATION_270 -> camera.setDisplayOrientation(180)
        }

        return previewSize
    }

    private fun calculateBufferSize(w: Int, h: Int): Int {
        val yStride = ceil(w / 16.0) * 16
        val uvStride = ceil(yStride / 2.0 / 16.0) * 16
        val ySize = yStride * h
        val uvSize = uvStride * h / 2
        return (ySize + uvSize * 2).toInt()
    }

}