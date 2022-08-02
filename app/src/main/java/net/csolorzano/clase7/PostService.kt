package net.csolorzano.clase7

import retrofit2.Call
import retrofit2.http.GET

interface PostService {

    @GET("posts")
    fun getPosts(): Call<List<Post>>
}