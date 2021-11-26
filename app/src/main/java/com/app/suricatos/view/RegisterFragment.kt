package com.app.suricatos.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.suricatos.databinding.FragmentRegisterBinding
import com.app.suricatos.model.request.Phone
import com.app.suricatos.model.request.RegisterUser
import com.app.suricatos.utils.DateUtils
import com.app.suricatos.utils.Mask
import com.app.suricatos.utils.Status
import com.app.suricatos.viewmodel.RegisterViewModel
import java.util.*

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
    ): View {
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

                    val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    findNavController().navigate(action)
                }
                Status.ERROR -> {
                    Log.d("ERRO DE REGISTRO", "${it.error}")
                }
            }//luis.carlos@gmail.com
        }

    }

    fun btnRegister() {
        binding.btnRegisterUser.setOnClickListener {
            val user = RegisterUser(
                binding.edtName.text.toString(),
                DateUtils.parseStringToDate(binding.edtBirthday.text.toString()),
                USER_TYPE_CIDADAO,
                binding.edtBiography.text.toString(),
                Phone(
                    binding.edtDDD.text.toString(),
                    binding.edtTelephone.text.toString(),
                    COMMERCIAL
                ),
                binding.edtEmail.text.toString(),
                binding.edtPass.text.toString(),
            )
            viewModel.register(user)
        }
    }

    private fun setupViews() {
        binding.edtBirthday.addTextChangedListener(Mask.Watcher(Mask.Format.DATE))
        binding.edtTelephone.addTextChangedListener(Mask.Watcher(Mask.Format.MOBILE_PHONE, Mask.Format.PHONE))

        validateName()
        validateEmail()
        validatePassword()
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
        binding.edtPass.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtPass.text.isEmpty() && binding.edtPass.text.isNullOrBlank()) {
                binding.edtPass.error = "Digite seu Senha"
            } else {
                binding.edtPass.error = null
            }
        }
    }

    companion object {
        val COMMERCIAL = "COMMERCIAL"
        val USER_TYPE_CIDADAO = "CIDADAO"
        val USER_TYPE_PREFEITURA = "PREFEITURA"
    }

}