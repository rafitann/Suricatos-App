package com.app.suricatos.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.suricatos.databinding.PostItemBinding
import com.app.suricatos.model.Post
import com.app.suricatos.model.response.FeedsDto
import com.app.suricatos.utils.load

class PostAdapter(private val postList: List<Post>? = arrayListOf()) : RecyclerView.Adapter<PostAdapter.ViewHolderPost>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPost {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderPost(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderPost, position: Int) {
        val post =  postList?.get(position) ?: return

        holder.binding.titlePost.text =post.title
        holder.binding.descriptionPost.text = post.description
        holder.binding.userNamePost.text = post.author.name
        holder.binding.tagPost.text = post.category
        holder.binding.commentNumberPost.text = "(${post.comments?.size ?: 0})"

        holder.binding.imagePost.load(post.id)
    }

    override fun getItemCount() = postList?.size ?: 0
    class ViewHolderPost(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)
}