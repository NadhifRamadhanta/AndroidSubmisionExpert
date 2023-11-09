package com.example.androidsubmision1.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidsubmision1.Activity.ProfileActivity
import com.example.androidsubmision1.GithubAdapter
import com.example.androidsubmision1.core.domain.model.ItemsItemModel
import com.example.androidsubmision1.favorite.databinding.ActivityFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModelDynamicFeatures : FavoriteViewModelDynamicFeatures by viewModel()
    private lateinit var dataCount: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(module)

        supportActionBar?.title = getString(R.string.user_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.rvFavorite.layoutManager = LinearLayoutManager(this)



        favoriteViewModelDynamicFeatures.getFavorite().observe(this) { data ->
            val items = arrayListOf<ItemsItemModel>()
            data.map {
                val item = ItemsItemModel(login = it.username, avatarUrl = it.avatarUrl ?: "")
                items.add(item)
            }
            dataCount = "${items.size}"
            Log.d("FavoriteViewModel", "${items.size}")
            val adapter = GithubAdapter(items, this@FavoriteActivity)
            binding.rvFavorite.adapter = adapter
            adapter.setOnItemClickCallback(object : GithubAdapter.OnItemClickCallback {
                override fun onItemClicked(data: String) {
                    startActivity(
                        Intent(
                            this@FavoriteActivity,
                            ProfileActivity::class.java
                        ).putExtra("USER_EXTRA", data)
                    )
                }
            })
        }

        binding.fab.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        this,
                        Class.forName("com.example.androidsubmision1.countfavorite.CountFavoriteActivities")
                    ).putExtra("dataCountFavorite", dataCount)
                )
            } catch (e: Exception) {
                Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}