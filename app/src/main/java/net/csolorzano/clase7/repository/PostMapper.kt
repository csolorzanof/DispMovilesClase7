package net.csolorzano.clase7.repository

import net.csolorzano.clase7.Post
import net.csolorzano.clase7.PostEntity

class PostMapper {

    fun serviceToEntity(post: Post) : PostEntity {
        return PostEntity(post.id,
        post.userId,
        post.title,
        post.body)
    }
}