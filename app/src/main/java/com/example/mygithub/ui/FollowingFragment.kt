package com.example.mygithub.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.R
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.data.retrofit.ApiConfig
import com.example.mygithub.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentFollowingBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.layoutManager = layoutManager

        val dataUser=arguments?.getString(DetailActivity.EXTRA_USER)
        println("ini data user $dataUser")

        getUserFollowing(dataUser.toString())
    }

    private fun getUserFollowing(name :String){
        showLoading(true)
        val client= ApiConfig.getApiService().followingUser(name)
        client.enqueue(object :Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                showLoading(false)
                val list=response.body()
                if (response.isSuccessful){
                    val adapter=UserAdapter()
                    adapter.submitList(list)
                    binding.rvFollowing.adapter=adapter
                    adapter.setOnItemClickCallback(object :UserAdapter.OnItemClickCallback{
                        override fun onItemClicked(data: ItemsItem) {
                            val mIntent= Intent(context,DetailActivity::class.java)
                            mIntent.putExtra(DetailActivity.EXTRA_USER,data.login)
                            startActivity(mIntent)
                        }

                    })
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading(false)
              Log.e("Failure",t.message.toString())
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }

}