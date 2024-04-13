package com.example.finalgithubuserlist.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalgithubuserlist.ui.detail.DetailViewModel

import com.example.finalgithubuserlist.ui.main.MainViewModel
import com.example.finalgithubuserlist.ui.favorite.FavoriteViewModel
import com.example.finalgithubuserlist.ui.setting.SettingViewModel
import com.example.finalgithubuserlist.ui.follow.FollowViewModel


class ViewModelFactory private constructor(private val application: Application, private val preferences: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application, preferences: SettingPreferences): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(application, preferences).also { INSTANCE = it}
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(application, preferences) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(application, preferences) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(preferences) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(application, preferences ) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}