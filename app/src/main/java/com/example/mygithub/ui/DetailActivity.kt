package com.example.mygithub.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithub.R
import com.example.mygithub.data.response.DetailGithubResponse
import com.example.mygithub.data.retrofit.ApiConfig
import com.example.mygithub.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    companion object {
        const val EXTRA_USER = "username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val dataUser= if (Build.VERSION.SDK_INT >= 33) {
            intent.getStringExtra(EXTRA_USER)
        } else {
            @Suppress("DEPRECATION")
            intent.getStringExtra(EXTRA_USER)
        }

        //send data to fragment
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, dataUser)
        val followerFragment = FollowerFragment()
        followerFragment.arguments = bundle
        val followingFragment = FollowingFragment()
        followingFragment.arguments = bundle





        getDetail(dataUser)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager:ViewPager2= binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs : TabLayout= binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()



    }

    private fun getDetail(username: String?) {
        showLoading(true)
        val client= ApiConfig.getApiService().detailUser(username.toString())
        client.enqueue(object: Callback<DetailGithubResponse>{
            override fun onResponse(
                call: Call<DetailGithubResponse>,
                response: Response<DetailGithubResponse>
            ) {
                showLoading(false)
                if(response.isSuccessful){
                    val responseBody = response.body()
                    Log.d("Hasil ini bang :",responseBody.toString())
                    if (responseBody != null) {
                        binding.tvGithubName.text = responseBody.login
                        binding.tvUsername.text = responseBody.name
                        binding.tvFollowers.text = getString(R.string.followers,responseBody.followers.toString())
                        binding.tvFollowing.text = getString(R.string.following,responseBody.following.toString())
                        Glide.with(this@DetailActivity)
                            .load(responseBody.avatarUrl)
                            .into(binding.imgUserDetail)
                    }
                }
            }

            override fun onFailure(call: Call<DetailGithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e("DetailActivity", "onFailure: ${t.message.toString()}" )
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