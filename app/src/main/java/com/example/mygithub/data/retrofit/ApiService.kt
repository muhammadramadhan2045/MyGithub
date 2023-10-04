package com.example.mygithub.data.retrofit

import com.example.mygithub.data.response.DetailGithubResponse
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.data.response.SearchGithubResponse
import retrofit2.Call
import retrofit2.http.*


//https://api.github.com/search/users?q=muhammadramadhan2045
interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchGithubResponse>

    @GET("users/{username}")
    fun detailUser(
        @Path("username") username: String
    ): Call<DetailGithubResponse>


    @GET("users/{username}/followers")
    fun followerUser(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun followingUser(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}