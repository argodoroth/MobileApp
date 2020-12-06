package com.example.newsmate.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsmate.MainActivity
import com.example.newsmate.NewsFragment
import com.example.newsmate.SavedFragment

//Class for controlling tabs of view pager
class TabAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        //will return different fragments based on the tab
        when (position) {
            0 -> return NewsFragment()
            1 -> return SavedFragment()
        }
        return NewsFragment()
    }

    //returns number of tabs
    override fun getItemCount(): Int {
        return 2
    }
}