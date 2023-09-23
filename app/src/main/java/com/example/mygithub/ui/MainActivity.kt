package com.example.mygithub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel=ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnClickListener {
                    searchBar.text=searchView.text
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    mainViewModel.setUsername(searchView.text.toString())
                    false
                }
        }



        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)





        mainViewModel.listUser.observe(this){
            setUsersData(it)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }




    }


    private fun setUsersData(items: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(items)
        binding.rvUsers.adapter = adapter
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                Toast.makeText(this@MainActivity, data.login, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, data.login)
                startActivity(intent)
            }

        })
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }
}