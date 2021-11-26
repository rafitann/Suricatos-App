package com.app.suricatos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.suricatos.model.Post
import com.app.suricatos.model.User
import com.app.suricatos.repository.PostRepository
import com.app.suricatos.repository.UserRepository
import com.app.suricatos.utils.AuthenticationRequiredException
import com.app.suricatos.utils.Resource
import kotlinx.coroutines.launch

class PostViewModel: ViewModel() {

    val user: MutableLiveData<Resource<User>> = MutableLiveData()
    val posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()

    val feed: MutableLiveData<Resource<Post>> = MutableLiveData()

    private val userRepository = UserRepository()
    private val postRepository = PostRepository()

    fun getUser() {
        viewModelScope.launch {
            try {
                user.postValue(Resource.success(userRepository.getUser()))
            } catch (e: Exception) {
                user.postValue(Resource.error("Não autorizado", AuthenticationRequiredException()))
            }
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            try {
                posts.postValue(Resource.success(postRepository.getPosts()))
            } catch (e: Exception) {
                posts.postValue(Resource.error("Não autorizado", AuthenticationRequiredException()))
            }
        }
    }

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