package com.app.suricatos.view

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.suricatos.databinding.FragmentEditProfileBinding
import suricatos.core.TakePictureActivity

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

    fun moveToCamera(){
        binding.btnTakePhoto.setOnClickListener {
//            startActivityForResult(Intent(this.requireContext(), TakePictureActivity::class.java),1)

        }
    }

}