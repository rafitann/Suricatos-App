package com.app.suricatos.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.suricatos.databinding.FragmentEditProfileBinding

class EditProfileFragment : BaseFragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveToCamera()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binding.imgPhoto.onActivityResult(requestCode,resultCode,data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        binding.imgPhoto.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }

    fun moveToCamera(){
        binding.btnChangePhoto.setOnClickListener {
            startActivity(Intent(this.requireContext(), CameraActivity::class.java))
        }
    }

}