package com.example.mygithub.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.databinding.ActivityFavoriteBinding
import com.example.mygithub.ui.GithubUserAdapter
import com.example.mygithub.ui.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding

    private lateinit var adapter: GithubUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val favoriteViewModel=obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavoriteUser().observe(this, { githubUserList ->
            if (githubUserList != null) {
                adapter.setListGithubUser(githubUserList)
            }
        })

        adapter= GithubUserAdapter()
        binding.rvGithubFavorite.layoutManager= LinearLayoutManager(this)
        binding.rvGithubFavorite.setHasFixedSize(true)
        binding.rvGithubFavorite.adapter=adapter



    }

    private fun obtainViewModel(activity: FavoriteActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}