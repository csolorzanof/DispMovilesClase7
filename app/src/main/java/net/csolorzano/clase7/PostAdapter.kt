package net.csolorzano.clase7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<PostViewHolder>() {

    private val posts = mutableListOf<PostEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = PostViewHolder(layoutInflater.inflate(R.layout.view_post, parent, false))
        return view
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setPosts(posts: List<PostEntity>){
        this.posts.clear()
        this.posts.addAll(posts)
        this.notifyDataSetChanged()
    }
}