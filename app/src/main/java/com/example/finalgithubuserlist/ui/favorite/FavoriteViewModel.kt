package com.example.finalgithubuserlist.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finalgithubuserlist.database.FavoriteGithubUser
import com.example.finalgithubuserlist.helper.FavoriteRepository
import com.example.finalgithubuserlist.helper.SettingPreferences

class FavoriteViewModel(private val application: Application, private val preferences: SettingPreferences) : ViewModel() {
    private val instanceFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getDataByAscending() : LiveData<List<FavoriteGithubUser>> {
        return instanceFavoriteRepository.getAllFavorite()
    }
}