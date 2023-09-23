package com.example.mygithub.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithub.data.response.DetailGithubResponse
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel:ViewModel() {
    private val  _detailUser= MutableLiveData<DetailGithubResponse>()
    val detailUser: LiveData<DetailGithubResponse> =_detailUser

    private val _followingUser= MutableLiveData<List<ItemsItem?>>()
    val followingUser: LiveData<List<ItemsItem?>> =_followingUser

    private val _followerUser= MutableLiveData<List<ItemsItem?>>()
    val followerUser: LiveData<List<ItemsItem?>> =_followerUser

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =_isLoading

    private val _isLoadingFollowing= MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> =_isLoadingFollowing

    private val _isLoadingFollower= MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> =_isLoadingFollower

    private val _noFollower= MutableLiveData<Boolean>()
    val noFollower: LiveData<Boolean> =_noFollower

    private val _noFollowing= MutableLiveData<Boolean>()
    val noFollowing: LiveData<Boolean> =_noFollowing



    private val _dataUser = MutableLiveData<String>()
    val dataUser: LiveData<String> = _dataUser

    var userDataName: String? = null
        set(value) {
            field = value
            setDataUser(value.toString())
        }

    fun setDataUser(username: String) {
        _dataUser.value = username
    }

    init {
        _dataUser.observeForever {
            getDetail(it)
            getUserFollower(it)
            getUserFollowing(it)
        }

    }


    private fun getDetail(username: String?) {
        if (username.isNullOrEmpty()) {
            return
        }
        _isLoading.value=true
        val client= ApiConfig.getApiService().detailUser(username.toString())
        client.enqueue(object: Callback<DetailGithubResponse> {
            override fun onResponse(
                call: Call<DetailGithubResponse>,
                response: Response<DetailGithubResponse>
            ) {
                _isLoading.value=false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    Log.d("Hasil ini bang :",responseBody.toString())
                    if (responseBody != null) {
                        _detailUser.value=responseBody!!

                    }
                }
            }

            override fun onFailure(call: Call<DetailGithubResponse>, t: Throwable) {
                _isLoading.value=false
                Log.e("DetailActivity", "onFailure: ${t.message.toString()}" )
            }
        })
    }

    private fun getUserFollowing(name :String){
        _isLoadingFollowing.value=true
        _noFollowing.value=false
        val client= ApiConfig.getApiService().followingUser(name)
        client.enqueue(object :Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollowing.value=false
                val list=response.body()
                if (response.isSuccessful){
                    _followingUser.value=list!!

                    if (list.isEmpty()){
                        _noFollowing.value=true
                        Log.d("List Following","Kosong")
                    }

                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollowing.value=false
                Log.e("Failure",t.message.toString())
            }
        })
    }

    private fun getUserFollower(name :String){
        _isLoadingFollower.value=true
        _noFollower.value=false
        val client= ApiConfig.getApiService().followerUser(name)
        client.enqueue(object :Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollower.value=false
                val list=response.body()
                if (response.isSuccessful){
                    _followerUser.value=list!!
                    if (list.isEmpty()){
                        _noFollower.value=true
                        Log.d("List Follower","Kosong")
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollower.value=false
                Log.e("Failure",t.message.toString())
            }
        })
    }

}