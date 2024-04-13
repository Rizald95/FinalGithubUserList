package com.example.finalgithubuserlist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteGithubDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoriteGithubUser)

    @Delete
    fun delete(favorite: FavoriteGithubUser)

    @Query
        ("SELECT * FROM FavoriteGithubUser WHERE username = :username")
    fun getUsernameGithubFavoriteByUsername(username: String): LiveData<FavoriteGithubUser>

    @Query
        ("SELECT * FROM FavoriteGithubUser ORDER BY username ASC")
    fun getAllFavoriteByAscending(): LiveData<List<FavoriteGithubUser>>
}