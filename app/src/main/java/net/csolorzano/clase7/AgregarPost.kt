package net.csolorzano.clase7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import net.csolorzano.clase7.repository.PostMapper
import net.csolorzano.clase7.repository.PostRepository
import net.csolorzano.clase7.repository.PostRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class AgregarPost : AppCompatActivity() {
    lateinit var postRepository: PostRepository
    val btnGuardar : Button by lazy {
        findViewById(R.id.btnGuardarPost)
    }

    val txtUsuario : EditText by lazy { findViewById(R.id.txtUserId) }
    val txtTitulo : EditText by lazy { findViewById(R.id.txtTituloPost) }
    val txtCotenido : EditText by lazy { findViewById(R.id.txtContenido) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_post)

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

        btnGuardar.setOnClickListener {
            val usuario = txtUsuario.text.toString().toLong()
            val titulo = txtTitulo.text.toString()
            val contenido = txtCotenido.text.toString()

            val registro = PostEntity(
                0,
                usuario,
                titulo,
                contenido
            )

            postRepository.agregarPost(registro)
            this.finish()
        }
    }
}