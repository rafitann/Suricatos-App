package com.app.suricatos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.suricatos.databinding.FragmentProfileBinding
import com.app.suricatos.utils.AuthenticationRequiredException
import com.app.suricatos.utils.Status
import com.app.suricatos.utils.extension.loadByUrl
import com.app.suricatos.view.adapters.PostAdapter
import com.app.suricatos.viewmodel.PostViewModel
import com.app.suricatos.viewmodel.ProfileViewModel
import com.app.suricatos.viewmodel.SettingsViewModel

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()
    private val postViewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservable()
        setupMyPost()

        profileViewModel.getUser()
        postViewModel.getPosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupMyPost() {
        binding.recylerMyPosts.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupObservable() {
        profileViewModel.user.observe(viewLifecycleOwner) {
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

        postViewModel.posts.observe(viewLifecycleOwner) {
            when(it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recylerMyPosts.adapter = PostAdapter(it.data)
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.error is AuthenticationRequiredException) {
                        val action = ProfileFragmentDirections.actionProfileFragmentToHomeFragment()
                        findNavController().navigate(action)

                        Toast.makeText(context, "Erro ao acessa suas postagens, tente fazer login novamente", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(context, "Falha generica", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }

}