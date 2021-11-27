package com.app.suricatos.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.CameraProfile
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.AttributeSet
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.util.*

class PhotosView : androidx.appcompat.widget.AppCompatImageView {

    private val REQUEST_PERMISSION_CODE = 666
    private lateinit var permissions: List<String>
    private var attachs: File? = null
    private var showCamera: Boolean = false
    private var directory: File? = null

    private var fragment: Fragment? = null

    constructor(context: Context, attrs: AttributeSet? = null) :
            super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        directory = context.externalCacheDir as File
        permissions =
            arrayListOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .filter {
                    ContextCompat.checkSelfPermission(
                        context,
                        it
                    ) != PackageManager.PERMISSION_GRANTED
                }
        setOnClickListener {
            openCamera()
        }
    }

     fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val results = grantResults.filter { it == PackageManager.PERMISSION_GRANTED }
                    if (results.size == permissions.size) {
                        this.permissions.toMutableList().clear()
                        Toast.makeText(context as Activity, "Camera habilitada", Toast.LENGTH_SHORT)
                            .show()
                        openCamera()
                    }
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                attachs?.let { setImage(it) }
            }
        }
    }

    private fun openCamera() {
        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                context as Activity,
                permissions.toTypedArray(),
                REQUEST_PERMISSION_CODE
            )
            return
        }

        showCamera = true
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        attachs = File(directory, "${UUID.randomUUID()}.png")

        val file = attachs!!
        if (file.exists()) file.delete()

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, "com.app.suricatos.provider", file)
        } else {
            Uri.fromFile(file)
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        intent.putExtra(
            MediaStore.EXTRA_SCREEN_ORIENTATION,
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        )
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, CameraProfile.QUALITY_HIGH)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        (context as AppCompatActivity).startActivityForResult(intent, 1)
    }

    private fun setImage(attach: File) {
        showCamera = false

        val path = attach.absolutePath
        val bitmap = BitmapFactory.Options().let {
            it.inJustDecodeBounds = false
            it.inSampleSize = 2
            BitmapFactory.decodeFile(path, it)?.let {
                val exif = ExifInterface(path)
                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)

                val m = Matrix()
                m.postRotate(
                    when (orientation) {
                        ExifInterface.ORIENTATION_ROTATE_180 -> 180F
                        ExifInterface.ORIENTATION_ROTATE_90 -> 90F
                        ExifInterface.ORIENTATION_ROTATE_270 -> 270F
                        else -> 0F
                    }
                )

                Bitmap.createBitmap(it, 0, 0, it.width, it.height, m, true)
            }
        }

        val file = File(path)
        Picasso.get().load(file).memoryPolicy(MemoryPolicy.NO_CACHE).fit().into(this)

        Handler(Looper.getMainLooper()).postDelayed({
            if (file.exists()) {
                try {
                    FileOutputStream(file).use {
                        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, it)
                    }
                } catch (e: Throwable) {
                }
            }
        }, 1000)
    }
}