package net.csolorzano.clase7

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: PostEntity)

    @Query("SELECT * FROM posts")
    fun loadPosts(): LiveData<List<PostEntity>>

}