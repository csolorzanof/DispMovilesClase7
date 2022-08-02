package net.csolorzano.clase7

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
    private val txtBody: TextView = itemView.findViewById(R.id.txtBody)

    fun bind(postEntity: PostEntity){
        txtTitulo.text = postEntity.title
        txtBody.text = postEntity.body
    }
}