package com.example.finalgithubuserlist.data.response
import com.google.gson.annotations.SerializedName

data class  ResponseDetailGithubUser(


    @field:SerializedName("following_url")
    val followingUrl: String,


    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,


    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String,

    )