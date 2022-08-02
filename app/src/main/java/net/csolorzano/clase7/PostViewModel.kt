package net.csolorzano.clase7

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import net.csolorzano.clase7.repository.PostRepository

class PostViewModel(
    private val postRepository: PostRepository
) : ViewModel() {

    fun getPosts() : LiveData<List<PostEntity>> {
        return postRepository.getPosts()
    }
}