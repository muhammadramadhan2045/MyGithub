package com.example.mygithub.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.data.response.SearchGithubResponse
import com.example.mygithub.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) :ViewModel() {

    private val  _listUser=MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> =_listUser

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =_isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val USERNAME= "muhammadramadhan"
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }







    init {
        findUser(USERNAME)
    }





    fun setUsername(name:String){
        findUser(name)
    }


    private fun findUser(
        name: String
    ) {
        _isLoading.value=true
        val client= ApiConfig.getApiService().searchUser(name)
        client.enqueue(object : Callback<SearchGithubResponse> {
            override fun onResponse(
                call: Call<SearchGithubResponse>,
                response: Response<SearchGithubResponse>
            ) {
                _isLoading.value=false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value=responseBody.items
                    }
                }
            }

            override fun onFailure(call: Call<SearchGithubResponse>, t: Throwable) {
                _isLoading.value=false
                Log.e(TAG,"onFailure : ${t.message.toString()}")
            }
        })
    }

}