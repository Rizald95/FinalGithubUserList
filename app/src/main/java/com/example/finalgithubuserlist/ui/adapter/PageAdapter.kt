package com.example.finalgithubuserlist.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalgithubuserlist.ui.follow.FollowFragment

class PageAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }
    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARGUMENT_POSITION, position + 1)
            putString(FollowFragment.ARGUMENT_USERNAME, username)
        }
        return fragment
    }
}