package com.app.suricatos.view

import android.os.Bundle
import com.app.suricatos.databinding.ActivityCameraBinding

class CameraActivity : BaseActivity() {

    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}