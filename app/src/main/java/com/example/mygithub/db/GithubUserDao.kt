package com.example.mygithub.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: GithubUser)

    @Update
    fun update(user: GithubUser)

    @Delete
    fun delete(user: GithubUser)

    @Query("SELECT*from githubuser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<GithubUser>

    @Query("SELECT*from githubuser")
    fun getAllFavoriteUser(): LiveData<List<GithubUser>>

}