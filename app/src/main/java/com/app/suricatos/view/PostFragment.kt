package com.app.suricatos.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.suricatos.databinding.FragmentPostBinding
import com.app.suricatos.utils.Status
import com.app.suricatos.viewmodel.PostViewModel

class PostFragment: BaseFragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private val postViewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtUserName.text = ""

        //setupListener()
        setupObservable()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun setupListener() {
//        binding.btnPublish.setOnClickListener {
//            val (validate, message) = isFormValidate()
//
//            if (validate) {
//
//                postViewModel.createPost(
//                    Post(
//                        title = "",
//                        category = "#buraco",
//                        description = binding.edtNewPost.text.toString(),
//                        author = Author(Cache.userName)
//                    )
//                )
//
//            } else {
//               showError(message)
//            }
//        }
//    }

    private fun setupObservable() {

        postViewModel.feed.observe(viewLifecycleOwner) {

            when(it.status) {

                Status.SUCCESS ->  {
                    val action = PostFragmentDirections.actionPostFragmentToHomeFragment()
                    findNavController().navigate(action)

                    Toast.makeText(requireContext(), "Postagem realizada com sucesso", Toast.LENGTH_LONG).show()
                }

                Status.ERROR -> {
                    showError(it.error?.message)
                }

            }

        }

    }

    private fun isFormValidate(): Pair<Boolean, String?> {
        return true to null
    }

    private fun showError(message: String? = "Houve um problema no envio de sua postagem") {
        AlertDialog.Builder(requireContext()).also {
            it.setTitle("Ops!!")
            it.setMessage(message)
            it.setCancelable(false)
            it.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
        }
    }

}