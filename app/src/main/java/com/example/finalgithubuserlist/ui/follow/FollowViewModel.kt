package com.example.finalgithubuserlist.ui.follow

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalgithubuserlist.data.response.ItemsItem
import com.example.finalgithubuserlist.data.retrofit.ApiConfig
import com.example.finalgithubuserlist.helper.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _FOLLOWERS = MutableLiveData<List<ItemsItem>>()
    private val _FOLLOWING = MutableLiveData<List<ItemsItem>>()
    private var _ISLOADING = MutableLiveData<Boolean>()
    private val _ISERROR = MutableLiveData<Boolean>()
    private var _ISLOADINGFOLLOW = MutableLiveData<Boolean>()

    val followers: LiveData<List<ItemsItem>> = _FOLLOWERS
    val following: LiveData<List<ItemsItem>> = _FOLLOWING
    val isLoading: LiveData<Boolean> = _ISLOADING
    val isError: LiveData<Boolean> = _ISERROR
    val isLoadingFollow: LiveData<Boolean> = _ISLOADINGFOLLOW


     fun getFollowersGithubUsername(username: String) {
        _ISERROR.value = false
        _ISLOADING.value = true
        val client = ApiConfig.getApiService().getFollowersGithub(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _ISLOADING.value = false
                if (response.isSuccessful) _FOLLOWERS.value = response.body() else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _ISLOADING.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

     fun getFollowingGithubUsername(username: String) {
        _ISERROR.value = false
        _ISLOADING.value = true
        val client = ApiConfig.getApiService().getFollowingGithub(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _ISLOADING.value = false
                if (response.isSuccessful) _FOLLOWING.value = response.body() else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _ISLOADING.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    companion object {private const val TAG = "DetailViewModel"}

}