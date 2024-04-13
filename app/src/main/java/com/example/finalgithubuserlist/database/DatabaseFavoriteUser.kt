package com.example.finalgithubuserlist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteGithubUser::class], version = 1, exportSchema = false)
abstract class DatabaseFavoriteUser: RoomDatabase() {
    abstract fun favoriteGithubDao(): FavoriteGithubDao
    companion object {
        @Volatile
        private var INSTANCE: DatabaseFavoriteUser? = null

        @JvmStatic
        @JvmSuppressWildcards
        fun getDatabase(context: Context): DatabaseFavoriteUser {
            if (INSTANCE == null) {
                synchronized(DatabaseFavoriteUser::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DatabaseFavoriteUser::class.java, "favoriteUser").build()
                }
            }
            return INSTANCE as DatabaseFavoriteUser
        }
    }
}