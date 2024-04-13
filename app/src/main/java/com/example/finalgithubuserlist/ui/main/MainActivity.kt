package com.example.finalgithubuserlist.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalgithubuserlist.R
import com.example.finalgithubuserlist.data.response.ItemsItem
import com.example.finalgithubuserlist.databinding.ActivityMainBinding
import com.example.finalgithubuserlist.helper.SettingPreferences
import com.example.finalgithubuserlist.helper.ViewModelFactory
import com.example.finalgithubuserlist.ui.adapter.UserAdapter
import com.example.finalgithubuserlist.ui.favorite.FavoriteActivity
import com.example.finalgithubuserlist.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SettingPreferences.PREFERENCES_KEY.THEME_KEY_SETTINGS)

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(
            application, SettingPreferences.getInstance(dataStore)
        )
    }
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val decorations = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(decorations)

        adapter = UserAdapter()
        binding.recyclerView.adapter= adapter

        mainViewModel.listUserGithub.observe(this) {
            listUserGithub -> setDataUsername(listUserGithub)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.SearchUsernameGithub(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getThemeSettingSelected().observe(this) {
            isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }



    }

    private fun setDataUsername(items: List<ItemsItem>) { adapter.submitList(items) }
    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}