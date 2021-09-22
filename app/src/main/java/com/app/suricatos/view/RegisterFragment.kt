package com.app.suricatos.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.suricatos.databinding.FragmentRegisterBinding
import com.app.suricatos.model.Address
import com.app.suricatos.model.User
import com.app.suricatos.utils.Cache
import com.app.suricatos.utils.Status
import com.app.suricatos.viewmodel.RegisterViewModel

class RegisterFragment : BaseFragment() {
    val viewModel: RegisterViewModel by viewModels()

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observable()
        btnRegister()
        setupViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observable() {
        viewModel.registerResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Cache.userName = binding.edtName.text.toString()
                    val sharedPref = context?.getSharedPreferences("session", Context.MODE_PRIVATE)
                    sharedPref?.edit {
                        putString("token", it.data)
                        putString("username", binding.edtName.text.toString())
                    }

                    val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
                Status.ERROR -> {
                    Log.d("ERRO DE REGISTRO", "${it.error}")
                }
            }
        }

    }

    fun btnRegister() {
        binding.btnRegister.setOnClickListener {
            val user = User(
                binding.edtName.text.toString(),
                binding.edtEmail.text.toString(),
                Address(
                    binding.edtAddress.text.toString(),
                    binding.edtDistrict.text.toString(),
                    binding.edtZipCode.text.toString(),
                    binding.edtCity.text.toString(),
                    binding.edtCountry.text.toString()
                ), binding.edtPasswordRegister.text.toString()
            )
            viewModel.register(user)
        }
    }

    private fun setupViews() {
        validateName()
        validateEmail()
        validatePassword()
        validateAddress()
        validateDistrict()
        validateCity()
        validateZipCode()
        validateCountry()
    }

    private fun validateName() {
        binding.edtName.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtName.text.isEmpty() && binding.edtName.text.isNullOrBlank()) {
                binding.edtName.error = "Digite seu Nome"
            } else {
                binding.edtName.error = null
            }
        }
    }

    private fun validateEmail() {
        binding.edtEmail.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtEmail.text.isEmpty() && binding.edtEmail.text.isNullOrBlank()) {
                binding.edtEmail.error = "Digite seu Email"
            } else {
                binding.edtEmail.error = null
            }
        }

    }

    private fun validatePassword() {
        binding.edtPasswordRegister.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtPasswordRegister.text.isEmpty() && binding.edtPasswordRegister.text.isNullOrBlank()) {
                binding.edtPasswordRegister.error = "Digite seu Senha"
            } else {
                binding.edtPasswordRegister.error = null
            }
        }
    }

    private fun validateAddress() {
        binding.edtAddress.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtAddress.text.isEmpty() && binding.edtAddress.text.isNullOrBlank()) {
                binding.edtAddress.error = "Digite seu Endereço"
            } else {
                binding.edtAddress.error = null
            }
        }
    }

    private fun validateDistrict() {
        binding.edtDistrict.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtDistrict.text.isEmpty() && binding.edtDistrict.text.isNullOrBlank()) {
                binding.edtDistrict.error = "Digite seu Bairro"
            } else {
                binding.edtDistrict.error = null
            }
        }
    }

    private fun validateCity() {
        binding.edtCity.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtCity.text.isEmpty() && binding.edtCity.text.isNullOrBlank()) {
                binding.edtCity.error = "Digite seu Cidade"
            } else {
                binding.edtCity.error = null
            }
        }
    }

    private fun validateZipCode() {
        binding.edtZipCode.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtZipCode.text.isEmpty() && binding.edtZipCode.text.isNullOrBlank()) {
                binding.edtZipCode.error = "Digite seu CEP"
            } else {
                binding.edtZipCode.error = null
            }
        }
    }

    private fun validateCountry() {
        binding.edtCountry.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtCountry.text.isEmpty() && binding.edtCountry.text.isNullOrBlank()) {
                binding.edtCountry.error = "Digite seu Estado"
            } else {
                binding.edtCountry.error = null
            }
        }
    }
}