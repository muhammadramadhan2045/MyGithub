package com.example.mygithub.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithub.data.response.ItemsItem

class MainViewModel :ViewModel() {

    private val  _listUser=MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> =_listUser

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =_isLoading

}