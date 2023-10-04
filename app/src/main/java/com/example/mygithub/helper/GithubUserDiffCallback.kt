package com.example.mygithub.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mygithub.db.GithubUser

class GithubUserDiffCallback(
    private val oldGithubUserList: List<GithubUser>,
    private val newGithubUserList: List<GithubUser>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldGithubUserList.size
    override fun getNewListSize(): Int = newGithubUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGithubUserList[oldItemPosition].username == newGithubUserList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldGithubUser = oldGithubUserList[oldItemPosition]
        val newGithubUser = newGithubUserList[newItemPosition]
        return oldGithubUser.username == newGithubUser.username && oldGithubUser.avatarUrl == newGithubUser.avatarUrl
    }

}