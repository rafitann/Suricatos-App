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
import com.app.suricatos.view.adapters.PostAdapter
import com.app.suricatos.viewmodel.PostViewModel

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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