package com.app.suricatos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.suricatos.databinding.FragmentPostDetailsBinding

class PostDetailsFragment : BaseFragment() {

    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


}