package suricatos.core

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import java.util.*


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NewCameraView: RelativeLayout, Device, TextureView.SurfaceTextureListener {
    private val ASPECT_RATIO = 3.0 / 4.0

    private var cameraId: String? = null
    private var previewSize: Size = Size(0,0)

    private var device: CameraDevice? = null
    private var camera: CameraCaptureSession? = null

    private var backgroundThread: HandlerThread? = null
    private var backgroundHandler: Handler? = null

    private var textureView: TextureView = AutoFitTextureView(context).also {
        it.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    private val stateCallback by lazy {
        object: CameraDevice.StateCallback() {
            override fun onOpened(device: CameraDevice) { createPreviewSession(device) }
            override fun onDisconnected(device: CameraDevice) { device.close() }
            override fun onError(device: CameraDevice, error: Int) { device.close() }
        }
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
        addView(textureView)
    }

    ////// TextureView.SurfaceTextureListener
    override fun onSurfaceTextureAvailable(texture: SurfaceTexture, width: Int, height: Int) {
        startCamera()
    }

    override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean = false

    override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture, width: Int, height: Int) {}
    override fun onSurfaceTextureUpdated(texture: SurfaceTexture) {}

    ////// Device
    override fun onResume() {
        if(textureView.isAvailable) {
            openBackgroundThread()
            startCamera()
        } else
            textureView.surfaceTextureListener = this
    }

    override fun onStop() {
        stopCamera()
        closeBackgroundThread()
    }

    override fun captureBitmap(picture: (bitmap: Bitmap) -> Unit) {
        stopCamera()
        closeBackgroundThread()
        textureView.bitmap?.let { picture.invoke(it) }
    }

    ////// Private
    @SuppressLint("MissingPermission")
    private fun startCamera() {
        val manager = (context as Activity).getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            val id = manager.cameraIdList.last()
            val characteristics = manager.getCameraCharacteristics(id)

            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            previewSize = map!!.getOutputSizes(SurfaceTexture::class.java)[0]

            manager.openCamera(id, stateCallback, backgroundHandler)
            cameraId = id

        } catch (e: Throwable) {}
    }

    private fun stopCamera() {
        camera?.close()
        camera = null

        device?.close()
        device = null
    }

    private fun createPreviewSession(device: CameraDevice) {
        this.device = device

        textureView.surfaceTexture?.let {
            it.setDefaultBufferSize(previewSize.width, previewSize.height)
            val surface = Surface(it)

            val request = device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            request.addTarget(surface)

            device.createCaptureSession(Collections.singletonList(surface), object: CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) {
                    try {
                        camera = session
                        session.setRepeatingRequest(request.build(), null, backgroundHandler)
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                }

                override fun onConfigureFailed(session: CameraCaptureSession) {}

            }, backgroundHandler)
        }
    }

    private fun openBackgroundThread() {
        backgroundThread = HandlerThread("camera_background_thread").also { it.start() }
        backgroundHandler = Handler(backgroundThread?.looper!!)
    }

    private fun closeBackgroundThread() {
        backgroundThread?.quitSafely()
        backgroundThread = null
        backgroundHandler = null
    }


    internal inner class AutoFitTextureView: TextureView {
        private var ratioWidth = 0
        private var ratioHeight = 0

        constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)
        constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int,
            defStyleRes: Int
        ):super(context, attrs, defStyleAttr, defStyleRes)


        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = MeasureSpec.getSize(heightMeasureSpec)

            if (ratioWidth == 0 || ratioHeight == 0) {
                setMeasuredDimension(width, height)
            } else {
                if (width < height * ratioWidth / ratioHeight) {
                    setMeasuredDimension(width, width * ratioHeight / ratioWidth)
                } else {
                    setMeasuredDimension(height * ratioWidth / ratioHeight, height)
                }
            }

//            var height = MeasureSpec.getSize(heightMeasureSpec)
//            var width = MeasureSpec.getSize(widthMeasureSpec)
//
//            val isPortrait =
//                resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
//
//            when {
//                isPortrait -> if (width > height * ASPECT_RATIO) {
//                    width = (height * ASPECT_RATIO + 0.5).toInt()
//                } else {
//                    height = (width / ASPECT_RATIO + 0.5).toInt()
//                }
//                else -> if (height > width * ASPECT_RATIO) {
//                    height = (width * ASPECT_RATIO + 0.5).toInt()
//                } else {
//                    width = (height / ASPECT_RATIO + 0.5).toInt()
//                }
//            }
//
//            setMeasuredDimension(width, height)
        }
    }

}