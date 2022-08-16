package net.csolorzano.clase7.repository

import androidx.lifecycle.LiveData
import net.csolorzano.clase7.PostEntity

interface PostRepository {

    fun getPosts(): LiveData<List<PostEntity>>

    fun agregarPost(post: PostEntity)
}