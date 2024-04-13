package com.example.finalgithubuserlist.ui.favorite

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.finalgithubuserlist.R
import com.example.finalgithubuserlist.databinding.ActivityFavoriteBinding
import com.example.finalgithubuserlist.helper.SettingPreferences
import com.example.finalgithubuserlist.helper.ViewModelFactory
import com.example.finalgithubuserlist.ui.adapter.UserAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalgithubuserlist.data.response.ItemsItem

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SettingPreferences.PREFERENCES_KEY.THEME_KEY_SETTINGS)

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application, SettingPreferences.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.idFavorite.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.idFavorite.addItemDecoration(itemDecoration)

        adapter = UserAdapter()
        binding.idFavorite.adapter = adapter

        favoriteViewModel.getDataByAscending().observe(this) {
            favorite ->
            val item = arrayListOf<ItemsItem>()
            favorite.map {
                val items = ItemsItem(login = it.username, avatarUrl = it.avatarUrl.toString())
                item.add(items)
            }
            adapter.submitList(item)
        }
    }
}