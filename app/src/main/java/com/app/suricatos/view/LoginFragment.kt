package com.app.suricatos.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.suricatos.R
import com.app.suricatos.databinding.FragmentLoginBinding
import com.app.suricatos.utils.Status
import com.app.suricatos.viewmodel.LoginViewModel

class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observable()
        btnAccess()
        navigateToRegister()
        setupViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observable() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val action = R.id.action_loginFragment_to_homeFragment
                    findNavController().navigate(action)
                }
                Status.ERROR -> {

                }
            }
        }
    }

    private fun btnAccess() {
        binding.btnLogin.setOnClickListener {
            viewModel.postLogin(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

    }

    private fun setupViews(){
        validateEmail()
        validatePassword()
    }

    private fun navigateToRegister() {
        binding.btnRegistration.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

    }

    private fun validateEmail() {
        binding.edtEmail.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtEmail.text.isEmpty()) {
                binding.edtEmail.error = "Preencha seu email"
            } else {
                binding.edtEmail.error = null
            }
        }

    }

    private fun validatePassword() {
        binding.edtPassword.setOnFocusChangeListener { _, hasFocus: Boolean ->
            if (hasFocus) return@setOnFocusChangeListener

            if (binding.edtPassword.text.isEmpty()) {
                binding.edtPassword.error = "Preencha sua senha"
            } else {
                binding.edtEmail.error = null
            }
        }
    }
}

