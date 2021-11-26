package com.app.suricatos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.suricatos.databinding.FragmentHomeBinding
import com.app.suricatos.utils.AuthenticationRequiredException
import com.app.suricatos.utils.Status
import com.app.suricatos.utils.extension.loadByUrl
import com.app.suricatos.view.adapters.PostAdapter
import com.app.suricatos.viewmodel.PostViewModel

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val postViewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPost()
        setupObservable()

        postViewModel.getUser()
        postViewModel.getPosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupPost() {
        binding.recyclerPost.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupObservable() {

        postViewModel.user.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> { }
                Status.SUCCESS -> {
                    binding.txtUserName.text = it.data?.name
                    binding.imgPhoto.loadByUrl(it.data?.image)
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.error is AuthenticationRequiredException) {
//                        Toast.makeText(
//                            context,
//                            "Ops.. Tente efetuar login novamente",
//                            Toast.LENGTH_LONG
//                        ).show()

                    } else {
                        Toast.makeText(context, "Falha generica", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }

        postViewModel.posts.observe(viewLifecycleOwner) {
            when (it.status) {

                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE

                    binding.recyclerPost.adapter = PostAdapter(it.data)
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.error is AuthenticationRequiredException) {
//                        val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
//                        findNavController().navigate(action)

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
        }
    }
}