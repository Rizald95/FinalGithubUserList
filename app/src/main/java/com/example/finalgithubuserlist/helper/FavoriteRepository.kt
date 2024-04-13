package com.example.finalgithubuserlist.helper

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.finalgithubuserlist.database.DatabaseFavoriteUser
import com.example.finalgithubuserlist.database.FavoriteGithubDao
import com.example.finalgithubuserlist.database.FavoriteGithubUser
import java.util.concurrent.Executors
import java.util.concurrent.ExecutorService

class FavoriteRepository(application: Application) {
    private val fGithubDao: FavoriteGithubDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val database = DatabaseFavoriteUser.getDatabase(application)
        fGithubDao = database.favoriteGithubDao()
    }

    fun insert(favorite: FavoriteGithubUser) {
        executorService.execute { fGithubDao.insert(favorite) }
    }
    fun delete(favorite: FavoriteGithubUser) {
        executorService.execute { fGithubDao.delete(favorite) }
    }
    fun getUsernameGithubFavoriteByUsername(username: String): LiveData<FavoriteGithubUser> {
        return fGithubDao.getUsernameGithubFavoriteByUsername(username)
    }
    fun getAllFavorite(): LiveData<List<FavoriteGithubUser>> = fGithubDao.getAllFavoriteByAscending()



}