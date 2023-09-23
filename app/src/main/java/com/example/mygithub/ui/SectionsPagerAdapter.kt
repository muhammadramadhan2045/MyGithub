package com.example.mygithub.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter (activity: AppCompatActivity): FragmentStateAdapter(activity){

    override fun createFragment(position: Int): Fragment {
       var fragment: Fragment?=null
        when(position){
            0->fragment= FollowerFragment()
            1->fragment= FollowingFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}