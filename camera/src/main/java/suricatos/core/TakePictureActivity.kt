package suricatos.core

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sofie.widget.camera.R

open class TakePictureActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION_CODE = 666
    private lateinit var permissions: List<String>

    private var cameraFragment: CameraFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        statusBarColor(android.R.color.transparent)

        permissions = arrayListOf(Manifest.permission.CAMERA)
            .filter {
                ContextCompat.checkSelfPermission(
                    applicationContext!!,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            }


        setContentView(R.layout.activity_camera)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissions.toTypedArray(),
                REQUEST_PERMISSION_CODE
            )
        } else showImagePickerOptions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val results = grantResults.filter { it == PackageManager.PERMISSION_GRANTED }
                    if (results.size == permissions.size) {
                        this.permissions.toMutableList().clear()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val image = data?.extras?.get("data") as Bitmap
                val bundlePhoto = Bundle()
                bundlePhoto.putParcelable("BitmapPhoto", image)
            }
        }
    }

    private fun showImagePickerOptions() {
//        supportFragmentManager.beginTransaction().add(R.id.content, ChoosePictureFragment().apply {
//            setOnTakePictureListener { onTakeCameraSelected() }
//            setOnTakeGalleryListener { launchGalleryIntent() }
//        }, "").commit()


        ChoosePictureFragment().apply {
            setOnTakePictureListener {
                dismiss()
                onTakeCameraSelected()
            }
            setOnTakeGalleryListener {
                dismiss()
                launchGalleryIntent()
            }
        }.show(supportFragmentManager, "choose")
    }


    private fun onTakeCameraSelected() {
        launchCameraIntent()
    }

    private fun onChooseGallerySelected() {
        launchGalleryIntent()
    }

    private fun launchCameraIntent() {
        cameraFragment = CameraFragment()
        supportFragmentManager.beginTransaction().replace(R.id.content, cameraFragment!!, "")
            .commit()

    }

    private fun launchGalleryIntent() {
    }

    private fun statusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(applicationContext, color)
        }
    }
}