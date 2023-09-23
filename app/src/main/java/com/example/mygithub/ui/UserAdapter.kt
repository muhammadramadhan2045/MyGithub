package com.example.mygithub.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.databinding.UserReviewBinding

class UserAdapter:ListAdapter<ItemsItem,UserAdapter.UserViewHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val binding = UserReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return UserViewHolder(binding)
        }

     override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
         holder.bind(user)
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(getItem(holder.adapterPosition))
            }

    }


    class UserViewHolder(val binding: UserReviewBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(user:ItemsItem){
            binding.tvName.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.imgUser)

        }
    }



    companion object{
        val DIFF_CALLBACK=object : DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem==newItem
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

}