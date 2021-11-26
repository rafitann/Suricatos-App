package com.app.suricatos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.suricatos.databinding.FragmentSettingsBinding
import com.app.suricatos.utils.AuthenticationRequiredException
import com.app.suricatos.utils.Status
import com.app.suricatos.utils.extension.loadByUrl
import com.app.suricatos.viewmodel.SettingsViewModel

class SettingsFragment : BaseFragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel: SettingsViewModel by viewModels()

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

        setupObservable()

        settingsViewModel.getUser()

        binding.btnEditProfile.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }

        binding.btnExit.setOnClickListener {
            settingsViewModel.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupObservable() {

        settingsViewModel.user.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    binding.imgPhoto.loadByUrl(it.data?.image)
                    binding.txtUserName.text = it.data?.name
                    binding.txtUserEmail.text = it.data?.email
                }

                Status.ERROR ->
                    if (it.error is AuthenticationRequiredException) {
                        Toast.makeText(
                            context,
                            "Ops.. Tente efetuar login novamente",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        Toast.makeText(context, "Falha generica", Toast.LENGTH_LONG).show()
                    }

            }
        }

        settingsViewModel.logout.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    requireActivity().finish()
                }
            }
        }
    }
}