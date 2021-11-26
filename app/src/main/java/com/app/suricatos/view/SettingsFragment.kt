package com.app.suricatos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.suricatos.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEditProfile.setOnClickListener {

            val action = SettingsFragmentDirections.actionSettingsFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}