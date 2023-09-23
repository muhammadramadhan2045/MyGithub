package com.example.mygithub.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.databinding.FragmentFollowingBinding



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

        val detailViewModel=ViewModelProvider(requireActivity(),ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        detailViewModel.isLoadingFollowing.observe(viewLifecycleOwner){
            showLoading(it)
        }

        detailViewModel.followingUser.observe(viewLifecycleOwner){
            setFollowingData(it)
        }

        detailViewModel.noFollowing.observe(viewLifecycleOwner){hasil->
            showData(hasil)
        }
    }


    private fun setFollowingData(list: List<ItemsItem?>) {
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


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun showData(loading:Boolean){
        if (loading){
            binding.noData.visibility=View.VISIBLE
        }else{
            binding.noData.visibility=View.GONE
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }

    }

}