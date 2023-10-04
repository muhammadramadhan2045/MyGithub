package com.example.mygithub.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GithubUser::class], version = 1)
abstract class GithubUserRoomDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): GithubUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(GithubUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubUserRoomDatabase::class.java, "githubuser_database"
                    )
                        .build()
                }
            }
            return INSTANCE as GithubUserRoomDatabase
        }
    }
}