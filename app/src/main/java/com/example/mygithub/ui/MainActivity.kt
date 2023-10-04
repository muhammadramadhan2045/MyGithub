package com.example.mygithub.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.R
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.databinding.ActivityMainBinding
import com.example.mygithub.ui.detail.DetailActivity
import com.example.mygithub.ui.favorite.FavoriteActivity
import com.example.mygithub.ui.main.MainViewModel
import com.example.mygithub.ui.main.SetThemeActivity
import com.example.mygithub.ui.main.SetThemeViewModelFactory
import com.example.mygithub.ui.main.SettingPreferences
import com.example.mygithub.ui.main.dataStore


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, SetThemeViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnClickListener {
                    searchBar.text = searchView.text
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    mainViewModel.setUsername(searchView.text.toString())
                    false
                }

        }

        val searchBar = binding.searchBar
        searchBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> {
                    val mIntent = Intent(this, SetThemeActivity::class.java)
                    startActivity(mIntent)
                    true
                }

                R.id.action_favorite -> {
                    val mIntent = Intent(this, FavoriteActivity::class.java)
                    startActivity(mIntent)
                    true
                }

                else -> true
            }
        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)



        mainViewModel.listUser.observe(this) {
            setUsersData(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }
        }


    }


    private fun setUsersData(items: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(items)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Toast.makeText(this@MainActivity, data.login, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, data.login)
                intent.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                startActivity(intent)
            }

        })
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }
}