package com.example.finalgithubuserlist.data.retrofit

import com.example.finalgithubuserlist.data.response.ItemsItem
import com.example.finalgithubuserlist.data.response.ResponseDetailGithubUser
import com.example.finalgithubuserlist.data.response.ResponseGithubUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceResponse {

    @GET("search/users")
    fun getUsersGithub (@Query("q") query: String,): Call <ResponseGithubUser>

    @GET("users/{username}")
    fun getDetailUserGithub(@Path("username") username: String?): Call <ResponseDetailGithubUser>


    @GET("users/{username}/followers")
    fun getFollowersGithub(@Path("username") username: String): Call<List<ItemsItem>>


    @GET("users/{username}/following")
    fun getFollowingGithub(@Path("username") username: String): Call<List<ItemsItem>>

}