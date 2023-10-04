package com.example.mygithub.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mygithub.db.GithubUser
import com.example.mygithub.db.GithubUserDao
import com.example.mygithub.db.GithubUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GithubUserRepository(application: Application) {
    private val mGithubUserDao: GithubUserDao
    private val excutorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GithubUserRoomDatabase.getDatabase(application)
        mGithubUserDao = db.githubUserDao()
    }

    fun insert(githubUser: GithubUser) {
        excutorService.execute { mGithubUserDao.insert(githubUser) }
    }

    fun delete(githubUser: GithubUser) {
        excutorService.execute { mGithubUserDao.delete(githubUser) }
    }

    fun update(githubUser: GithubUser) {
        excutorService.execute { mGithubUserDao.update(githubUser) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<GithubUser> =
        mGithubUserDao.getFavoriteUserByUsername(username)

    fun getAllFavoriteUser(): LiveData<List<GithubUser>> = mGithubUserDao.getAllFavoriteUser()

}