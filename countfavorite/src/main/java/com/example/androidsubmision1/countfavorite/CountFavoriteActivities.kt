package com.example.androidsubmision1.countfavorite

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidsubmision1.countfavorite.databinding.ActivityCountFavoriteActivitiesBinding


class CountFavoriteActivities : AppCompatActivity() {
    private lateinit var binding: ActivityCountFavoriteActivitiesBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountFavoriteActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.total_favorited)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dataCount = intent.getStringExtra("dataCountFavorite")
        binding.tvCount.text = "Your Have $dataCount User as Favorite"


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}