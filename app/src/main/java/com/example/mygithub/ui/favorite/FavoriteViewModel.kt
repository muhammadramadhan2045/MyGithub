package com.example.mygithub.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mygithub.db.GithubUser
import com.example.mygithub.repo.GithubUserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mGithubUserRepository: GithubUserRepository = GithubUserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<GithubUser>> =
        mGithubUserRepository.getAllFavoriteUser()

    fun getFavoriteUserByUsername(username: String): LiveData<GithubUser> =
        mGithubUserRepository.getFavoriteUserByUsername(username)
}