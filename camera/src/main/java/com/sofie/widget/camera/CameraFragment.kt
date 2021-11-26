package com.sofie.widget.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.steelkiwi.cropiwa.config.InitialPosition
import kotlinx.android.synthetic.main.fragment_camera.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig
import com.steelkiwi.cropiwa.shape.CropIwaRectShape


class CameraFragment: Fragment() {

    private var tempFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_camera, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_camera.visibility = View.VISIBLE
        layout_crop.visibility = View.GONE

        button_capture.setOnClickListener {
            layout_camera.visibility = View.GONE
            layout_crop.visibility = View.VISIBLE

            cropview.configureOverlay().setCropShape(CropIwaRectShape(cropview.configureOverlay())).apply()
            cropview.configureImage().setImageInitialPosition(InitialPosition.CENTER_CROP).apply()

            cameraview.captureImage {
                var tempDir = requireActivity().externalCacheDir!!
                tempDir = File(tempDir.absolutePath, "takephoto")
                if (tempDir.exists()) tempDir.deleteRecursively()
                tempDir.mkdir()

                tempFile = File.createTempFile(UUID.randomUUID().toString(), ".png", tempDir)
                val bytes = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                val bitmapData = bytes.toByteArray()

                //write the bytes in file
                val fos = FileOutputStream(tempFile)
                fos.write(bitmapData)
                fos.flush()
                fos.close()

                cropview.setImageUri(Uri.fromFile(tempFile))
            }
        }

        button_confirm.setOnClickListener {

            val uri = Uri.fromFile(tempFile)

            cropview.crop(
                CropIwaSaveConfig.Builder(uri)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .setQuality(100) //Hint for lossy compression formats
                    .build()
            )

            activity?.setResult(Activity.RESULT_OK, Intent().also { it.putExtra("photo", tempFile) })
            activity?.finish()
        }

        button_exit.setOnClickListener {
            layout_camera.visibility = View.VISIBLE
            layout_crop.visibility = View.GONE

            tempFile?.deleteRecursively()
            cameraview.onResume()
        }
    }

    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        requireActivity().window.apply {
            setLayout(width, height)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarColor = Color.TRANSPARENT
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cameraview.onResume()
    }

    override fun onStop() {
        super.onStop()
        cameraview.onStop()
    }
}