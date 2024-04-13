package com.example.finalgithubuserlist.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalgithubuserlist.data.response.ItemsItem
import com.example.finalgithubuserlist.data.response.ResponseGithubUser
import com.example.finalgithubuserlist.data.retrofit.ApiConfig
import com.example.finalgithubuserlist.helper.SettingPreferences
import retrofit2.Response
import android.util.Log
import androidx.lifecycle.asLiveData
import retrofit2.Call
import retrofit2.Callback

class MainViewModel(private val application: Application, private val preferences: SettingPreferences) : ViewModel() {
    private val _LISTUSERGITHUB = MutableLiveData<List<ItemsItem>>()
    private val _ISLOADING = MutableLiveData<Boolean>()
    private val _ISERROR = MutableLiveData<Boolean>()

    val listUserGithub: LiveData<List<ItemsItem>> = _LISTUSERGITHUB
    val isLoading: LiveData<Boolean> = _ISLOADING
    val isError: LiveData<Boolean> = _ISERROR

    init {
        findUsernameGithub(USERNAME_GITHUB)
    }

    private fun findUsernameGithub(query: String) {
        _ISERROR.value = false
        _ISLOADING.value = true
        val client = ApiConfig.getApiService().getUsersGithub(query)
        client.enqueue(object: Callback<ResponseGithubUser> {
            override fun onResponse(
                call: Call<ResponseGithubUser>,
                response: Response<ResponseGithubUser>
            ) {
                _ISLOADING.value = false
                if (response.isSuccessful) {
                    _LISTUSERGITHUB.value = response.body()?.items
                }
                else {
                    Log.e(TAG, "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseGithubUser>, t: Throwable) {
                Log.e(TAG, "onFailure ${t.message}")
            }
        })
    }

    fun getThemeSettingSelected(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    fun SearchUsernameGithub(name: String) {
        _ISLOADING.value = true
        val client = ApiConfig.getApiService().getUsersGithub(name)
        client.enqueue(object: Callback<ResponseGithubUser> {
            override fun onResponse(
                call: Call<ResponseGithubUser>,
                response: Response<ResponseGithubUser>
            ) {

                if (response.isSuccessful) {
                    _ISLOADING.value = false
                    _LISTUSERGITHUB.value = response.body()?.items
                }
                else {
                    Log.e(TAG, "onFailure ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseGithubUser>, t: Throwable) {
                Log.e(TAG, "onFailure ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
        private const val USERNAME_GITHUB = "Rizal"

    }

}