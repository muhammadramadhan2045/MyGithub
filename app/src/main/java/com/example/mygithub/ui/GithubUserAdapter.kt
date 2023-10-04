package com.example.mygithub.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithub.databinding.UserReviewBinding
import com.example.mygithub.db.GithubUser
import com.example.mygithub.helper.GithubUserDiffCallback
import com.example.mygithub.ui.detail.DetailActivity

class GithubUserAdapter : RecyclerView.Adapter<GithubUserAdapter.GithubUserViewHolder>() {
    private val listGithubUser = ArrayList<GithubUser>()
    fun setListGithubUser(list: List<GithubUser>) {
        val diffCallback = GithubUserDiffCallback(this.listGithubUser, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listGithubUser.clear()
        this.listGithubUser.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        val binding = UserReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        val githubUser = listGithubUser[position]
        holder.bind(githubUser)
    }

    override fun getItemCount(): Int {
        return listGithubUser.size
    }


    inner class GithubUserViewHolder(private val binding: UserReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUser: GithubUser) {
            with(binding) {
                tvName.text = githubUser.username
                Glide.with(itemView.context)
                    .load(githubUser.avatarUrl)
                    .into(binding.imgUser)
                cvItemUserReview.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, githubUser.username)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}