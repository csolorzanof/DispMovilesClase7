package net.csolorzano.clase7

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PostEntity::class],
    version = 1
)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
}