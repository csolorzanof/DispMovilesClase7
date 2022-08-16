package net.csolorzano.clase7.repository

import androidx.lifecycle.LiveData
import net.csolorzano.clase7.Post
import net.csolorzano.clase7.PostDao
import net.csolorzano.clase7.PostEntity
import net.csolorzano.clase7.PostService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class PostRepositoryImpl (
    private val postService: PostService,
    private val postDao: PostDao,
    private val postMapper: PostMapper,
    private val executor: Executor
        ): PostRepository {

    override fun getPosts(): LiveData<List<PostEntity>> {
        postService.getPosts().enqueue(object: Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                response.body()?.let { posts ->
                    executor.execute{
                        postDao.insertPosts(posts.map {
                            post-> postMapper.serviceToEntity(post)
                        })
                    }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {

            }

        })
        return postDao.loadPosts()
    }

    override fun agregarPost(post: PostEntity) {
        executor.execute {
            postDao.insertPost(post)
        }
    }
}