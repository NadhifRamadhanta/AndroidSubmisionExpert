package com.example.androidsubmision1.Activity

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidsubmision1.GithubAdapter
import com.example.androidsubmision1.ViewModel.MainViewModel
import com.example.androidsubmision1.R
import com.example.androidsubmision1.ViewModel.SettingViewModel
import com.example.androidsubmision1.core.domain.model.ItemsItemModel
import com.example.androidsubmision1.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    private val settingViewModel: SettingViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.github_user_list)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        mainViewModel.userList.observe(this) { data ->
            setDataUser(data)
        }

        mainViewModel.isLoading.observe(this) { showLoading(it) }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager

        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser = query
                mainViewModel.refresh()
                if (mainViewModel.userList.value?.size == 0) {
                    Toast.makeText(this@MainActivity, "User Not Found", Toast.LENGTH_SHORT).show()
                }
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length == 0) {
                    mainViewModel.searchUser = null
                    mainViewModel.refresh()

                }
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> true
            R.id.favorite -> {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("user-github://favorite")))
                    true
                } catch (e: Exception) {
                    Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
                    false
                }
            }

            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun setDataUser(dataUser: List<ItemsItemModel>?) {
        if (dataUser != null) {
            val rcUser = ArrayList<ItemsItemModel>(dataUser)
            val adapter = GithubAdapter(rcUser, this@MainActivity)
            binding.rvUser.adapter = adapter
            adapter.setOnItemClickCallback(object : GithubAdapter.OnItemClickCallback {
                override fun onItemClicked(data: String) {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            ProfileActivity::class.java
                        ).putExtra("USER_EXTRA", data)
                    )
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}