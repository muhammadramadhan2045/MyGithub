package com.example.mygithub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.data.response.SearchGithubResponse
import com.example.mygithub.data.retrofit.ApiConfig
import com.example.mygithub.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object {
        private const val TAG = "MainActivity"
        private const val USERNAME = "muhammadramadhan"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnClickListener {
                    searchBar.text=searchView.text
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    findUser(searchView.text.toString())
                    false
                }
        }



        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        findUser(USERNAME)






    }

    private fun findUser(name: String) {
        showLoading(true)
        val client=ApiConfig.getApiService().searchUser(name)
        client.enqueue(object :Callback<SearchGithubResponse>{
            override fun onResponse(
                call: Call<SearchGithubResponse>,
                response: Response<SearchGithubResponse>
            ) {
                showLoading(false)
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUsersData(responseBody.items)

                    }
                }
            }

            override fun onFailure(call: Call<SearchGithubResponse>, t: Throwable) {
               showLoading(false)
                Log.e(TAG,"onFailure : ${t.message.toString()}")
            }
        })
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