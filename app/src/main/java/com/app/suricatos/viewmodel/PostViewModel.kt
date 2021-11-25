package com.app.suricatos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.suricatos.model.Post
import com.app.suricatos.repository.PostRepository
import com.app.suricatos.utils.AuthenticationRequiredException
import com.app.suricatos.utils.Resource
import kotlinx.coroutines.launch

class PostViewModel: ViewModel() {

    val posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()

    val feed: MutableLiveData<Resource<Post>> = MutableLiveData()

    private val repository = PostRepository()


//    fun getPosts() {
//        viewModelScope.launch {
//            try {
//                posts.postValue(Resource.success(repository.getPosts()))
//            } catch (e: Exception) {
//                posts.postValue(Resource.error("Não autorizado", AuthenticationRequiredException()))
//            }
//        }
//    }

//    fun createPost(post: Post) {
//        viewModelScope.launch {
//            try {
//                feed.postValue(Resource.success(repository.createPost(post)))
//            } catch (e: Exception) {
//                feed.postValue(Resource.error("Não autorizado", AuthenticationRequiredException()))
//            }
//        }
//
//
//    }


}