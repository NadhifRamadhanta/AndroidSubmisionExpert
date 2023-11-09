package com.example.androidsubmision1.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.androidsubmision1.Adapter.PagerAdapter
import com.example.androidsubmision1.ViewModel.DetailViewModel
import com.example.androidsubmision1.GithubAdapter
import com.example.androidsubmision1.R
import com.example.androidsubmision1.ViewModel.FavoriteViewModel
import com.example.androidsubmision1.core.domain.model.ProfileResponseModel
//import com.example.androidsubmision1.ViewModel.ViewModelFactory
import com.example.androidsubmision1.databinding.ActivityProfileBinding
import com.example.androidsubmission1.core.domain.model.Favorite
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val detailViewModel: DetailViewModel by viewModel()
    private val favoriteViewModel: FavoriteViewModel by viewModel()



    companion object {
        const val USER_EXTRA = "USER_EXTRA"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val usernameActivity = intent.getStringExtra(USER_EXTRA)
        val favorited = Favorite()

        supportActionBar?.title = usernameActivity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.username = usernameActivity

        favorited.username = usernameActivity!!

        runBlocking {
            launch(Dispatchers.IO) {
                detailViewModel.getUser(usernameActivity)
            }
        }

        detailViewModel.detailUser.observe(this) { data ->
            favorited.avatarUrl = data.avatarUrl
            setDataToView(data)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val pagerAdapter = PagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = pagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab,    position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
            Log.d("ProfileActivity","$detailViewModel")

        favoriteViewModel.getUsername(usernameActivity).observe(this) { favorite ->
            Log.d("ProfileActivity","$favorite")
            if (favorite?.username == usernameActivity) {
                binding.favoriteButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.favoriteButton.context,
                        R.drawable.baseline_favorite_24
                    )
                )
                Log.d("TEST FAVORITED", "FAVORITED TRUE $favorite")
            } else {
                binding.favoriteButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.favoriteButton.context,
                        R.drawable.baseline_favorite_border_24
                    )
                )
                Log.d("TEST FAVORITED", "FAVORITED FALSE $favorite")
            }

            binding.favoriteButton.setOnClickListener {
                if (favorite?.username == usernameActivity) {
                    favoriteViewModel.delete(favorited)
                } else {
                    runBlocking {
                        launch(Dispatchers.IO) {
                            favoriteViewModel.saveUserFavorite(favorited)
                        }
                    }
                }
            }
        }

        GithubAdapter(arrayListOf(), this).setOnItemClickCallback(object :
            GithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {

            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun setDataToView(users: ProfileResponseModel) {
        Glide.with(this).load(users.avatarUrl).into(binding.circleImageView)
        binding.name.text = users.name
        binding.username.text = users.login
        binding.location.text = users.location
        binding.followers.text = "${users.followers} Followers"
        binding.following.text = "${users.following} Following"
        binding.gists.text = "${users.publicGists} Gists"
        binding.repos.text = "${users.publicRepos} Repos"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}