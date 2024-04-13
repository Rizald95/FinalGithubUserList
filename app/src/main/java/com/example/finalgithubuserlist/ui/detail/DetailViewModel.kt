package com.example.finalgithubuserlist.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalgithubuserlist.data.response.ResponseDetailGithubUser
import com.example.finalgithubuserlist.data.retrofit.ApiConfig
import com.example.finalgithubuserlist.helper.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.finalgithubuserlist.database.FavoriteGithubUser
import com.example.finalgithubuserlist.helper.FavoriteRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val application: Application, private val preferences: SettingPreferences) : ViewModel() {

    private val instanceFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    //load data
    private val _ISLOADING = MutableLiveData<Boolean>()
    private  val _ISERROR = MutableLiveData<Boolean>()

    //detail
    private val _DETAILLIST = MutableLiveData<ResponseDetailGithubUser>()


    //initialization
    val isLoading: LiveData<Boolean> = _ISLOADING
    val isError: LiveData<Boolean> = _ISERROR
    val detailList: LiveData<ResponseDetailGithubUser> = _DETAILLIST

    fun detailUsernameGithub(username: String?) {
        _ISERROR.value = false
        _ISLOADING.value = true
        val client = ApiConfig.getApiService().getDetailUserGithub(username)
        client.enqueue(object: Callback<ResponseDetailGithubUser> {
            override fun onResponse(
                call: Call<ResponseDetailGithubUser>,
                response: Response<ResponseDetailGithubUser>
            ) {
                _ISLOADING.value = false
                if (response.isSuccessful) _DETAILLIST.value = response.body() else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<ResponseDetailGithubUser>, t: Throwable) {
                _ISLOADING.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getThemeSettingSelected(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }
    fun saveThemeSettingSelected(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }

    fun insertDataFavoriteList(username: String?, avatarUrl: String?) {
        val user = if (avatarUrl != null) FavoriteGithubUser(username!!, avatarUrl) else {
            val detail = _DETAILLIST.value
            if (detail != null) {
                FavoriteGithubUser(username!!, detail.avatarUrl)
            } else {
                FavoriteGithubUser(username!!, null)
            }
        }
        instanceFavoriteRepository.insert(user)
    }

    fun deleteDataFavoriteList(favorite: FavoriteGithubUser)    {
        instanceFavoriteRepository.delete( favorite)
    }

    fun getDataByUsername(username: String?): LiveData<FavoriteGithubUser> {
        return instanceFavoriteRepository.getUsernameGithubFavoriteByUsername(username!!)
    }





    companion object {
        private const val TAG = "DetailViewModel"
    }



}