package net.csolorzano.clase7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import net.csolorzano.clase7.repository.PostMapper
import net.csolorzano.clase7.repository.PostRepository
import net.csolorzano.clase7.repository.PostRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    lateinit var postRepository: PostRepository
    lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postService = retrofit.create<PostService>(PostService::class.java)

        val postsDatabase = Room.databaseBuilder(applicationContext, PostDatabase::class.java, "post-db")
            .build()

        postRepository = PostRepositoryImpl(
            postService,
            postsDatabase.postDao(),
            PostMapper(),
            Executors.newSingleThreadExecutor()
        )

        postAdapter = PostAdapter(layoutInflater)
        val rcv = findViewById<RecyclerView>(R.id.rcvPosts)
        rcv.adapter = postAdapter
        rcv.layoutManager = LinearLayoutManager(this)

        val postViewModel = ViewModelProvider(this, object :
        ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PostViewModel(postRepository) as T
            }
        }
        ).get(PostViewModel::class.java)

        postViewModel.getPosts().observe(this, Observer{
            postAdapter.setPosts(it)
        })
    }
}