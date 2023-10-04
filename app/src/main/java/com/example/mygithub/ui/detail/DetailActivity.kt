package com.example.mygithub.ui.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithub.R
import com.example.mygithub.data.response.DetailGithubResponse
import com.example.mygithub.databinding.ActivityDetailBinding
import com.example.mygithub.db.GithubUser
import com.example.mygithub.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding


    private var isFavorite = false
    private var githubUser: GithubUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val detailViewModel = obtainViewModel(this@DetailActivity)

        val dataUser = intent.getStringExtra(EXTRA_USER)

        val dataImg = intent.getStringExtra(EXTRA_AVATAR)


        if (dataUser != null) {
            detailViewModel.userDataName = dataUser
        } else {
            Log.d("DetailActivity", "dataUser is null")
        }


        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.detailUser.observe(this) {
            setDetailData(it)
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        githubUser = intent.getParcelableExtra(EXTRA_USER)
        if (githubUser != null) {
            isFavorite = true

        } else {
            githubUser = GithubUser(
                username = dataUser.toString(),
                avatarUrl = dataImg.toString()
            )
        }



        if (dataUser != null) {
            detailViewModel.getFavoriteUserByUsername(dataUser).observe(this) {
                if (it != null) {
                    showFavoriteState(true)
                } else {
                    showFavoriteState(false)
                }
            }
        } else {
            Log.d("DetailActivity", "dataUser is null")
        }




        binding.fabFavorite.setOnClickListener {
            if (isFavorite) {
                showFavoriteState(false)
                detailViewModel.delete(githubUser!!)
            } else {
                detailViewModel.insert(githubUser!!)
                showFavoriteState(true)
                val userName = dataUser.toString()
                val avatarUrl = detailViewModel.detailUser.value?.avatarUrl.toString()

                githubUser = GithubUser(
                    username = userName,
                    avatarUrl = avatarUrl
                )

                detailViewModel.insert(githubUser!!)


            }
            isFavorite = !isFavorite


        }
    }

    private fun showFavoriteState(b: Boolean) {
        if (b) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun obtainViewModel(activity: DetailActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }


    private fun setDetailData(responseBody: DetailGithubResponse) {
        binding.tvGithubName.text = responseBody.login
        binding.tvUsername.text = responseBody.name
        binding.tvFollowers.text = getString(R.string.followers, responseBody.followers.toString())
        binding.tvFollowing.text = getString(R.string.following, responseBody.following.toString())
        Glide.with(this@DetailActivity).load(responseBody.avatarUrl).into(binding.imgUserDetail)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }


    fun setDataName(dataUser: String): String {
        val name = if (Build.VERSION.SDK_INT >= 33) {
            intent.getStringExtra(EXTRA_USER)
        } else {
            @Suppress("DEPRECATION") intent.getStringExtra(EXTRA_USER)
        }
        return name.toString()
    }

    companion object {
        const val EXTRA_USER = "username"
        const val EXTRA_AVATAR = "avatar_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1, R.string.tab_text_2
        )

    }


}