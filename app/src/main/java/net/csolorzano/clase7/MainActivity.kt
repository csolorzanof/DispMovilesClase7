package net.csolorzano.clase7

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
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
    lateinit var postViewModel: PostViewModel
    val btnAgregar : Button by lazy { findViewById(R.id.btnAgregar) }
    val actResultado = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        getResultados()
    }

    val swtModo : Switch by lazy { findViewById(R.id.swtModo) }

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

        postViewModel = ViewModelProvider(this, object :
        ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PostViewModel(postRepository) as T
            }
        }
        ).get(PostViewModel::class.java)

        getResultados()

        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarPost::class.java)
            actResultado.launch(intent)
        }

        val prefs = getSharedPreferences("postprefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        var modoDark = prefs.getBoolean("modo-dark", false)
        cambiarColorFondo(modoDark, rcv, editor)
        swtModo.setOnCheckedChangeListener { compoundButton, esModoDark ->
            cambiarColorFondo(esModoDark, rcv, editor)
        }
    }

    fun cambiarColorFondo(modo:Boolean, rcv: RecyclerView, editor: SharedPreferences.Editor){
        if(modo){
            rcv.setBackgroundColor(Color.parseColor("#363636"))
            swtModo.isChecked = true
        }else{
            rcv.setBackgroundColor(Color.parseColor("#FFFFFF"))
            swtModo.isChecked = false
        }
        editor.putBoolean("modo-dark", modo)
        editor.apply()
    }

    fun getResultados(){
        postViewModel.getPosts().observe(this, Observer{
            postAdapter.setPosts(it)
        })
    }
}