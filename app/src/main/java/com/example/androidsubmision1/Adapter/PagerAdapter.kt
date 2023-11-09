package com.example.androidsubmision1.Adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidsubmision1.Fragment.FollowFragment

class PagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private var username: String? = activity.intent.getStringExtra("USER_EXTRA")

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment(username!!)
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position + 1)
            putString(FollowFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}