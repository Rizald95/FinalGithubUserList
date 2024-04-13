package com.example.finalgithubuserlist.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import coil.load
import coil.transform.CircleCropTransformation
import com.example.finalgithubuserlist.R
import com.example.finalgithubuserlist.data.response.ResponseDetailGithubUser
import com.example.finalgithubuserlist.database.FavoriteGithubUser
import com.example.finalgithubuserlist.databinding.ActivityDetailBinding
import com.example.finalgithubuserlist.helper.SettingPreferences
import com.example.finalgithubuserlist.helper.ViewModelFactory
import com.example.finalgithubuserlist.ui.adapter.PageAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SettingPreferences.PREFERENCES_KEY.THEME_KEY_SETTINGS)

    private val detailViewModel by viewModels<DetailViewModel> ()
        {
        ViewModelFactory.getInstance(
            application, SettingPreferences.getInstance(dataStore)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.show()

        val pageAdapter = PageAdapter(this)
        binding.viewPager.adapter = pageAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {
                tab, position -> tab.text = getString(TAB_TITLES[position])
        }.attach()
        val username = intent.getStringExtra(USERNAME)
        val avatarUrl = intent.getStringExtra(AVATAR_URL)
        pageAdapter.username = username.toString()



        detailViewModel.detailList.observe(this) { detailList ->  setDetailList(detailList)}
        detailViewModel.isLoading.observe(this) {isLoading -> Loading(isLoading) }
        if (username != null) detailViewModel.detailUsernameGithub(username)



        var isFavorite = false
        detailViewModel.getDataByUsername(username).observe(this) {
            favorite -> if (favorite == null) {
                binding.btnFavorite.changeIconColor(R.color.light_primary)
            isFavorite = false
        } else {
            binding.btnFavorite.changeIconColor(R.color.night_accent)
            isFavorite = true
        }
        }
        binding.btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                username?.let {
                    detailViewModel.insertDataFavoriteList(it, avatarUrl)
                }
            } else {
                username?.let {
                    detailViewModel.deleteDataFavoriteList(FavoriteGithubUser(it, it))
                }
            }
        }
        observeThemeSettings()

    }

//

//    private fun setupViewModelObservers() {
//
//
//    }

    private fun Loading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun FloatingActionButton.changeIconColor(colorRes: Int) {
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, colorRes))
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailList(user: ResponseDetailGithubUser) {
        binding.apply {
            tvName.text = user.login
            tvUsername.text = user.name
            idProfile.load(user.avatarUrl) {
                transformations(CircleCropTransformation())
            }
            tvTotalFollowers.text = "${user.followers} Followers"
            tvTotalFollowing.text = "${user.following} Following"
        }
    }

    private fun observeThemeSettings() {
        detailViewModel.getThemeSettingSelected().observe(this) { isDarkModeActive ->
            val mode = if (isDarkModeActive) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }


    companion object {
        const val USERNAME = "username"
        const val AVATAR_URL = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tabText1,
            R.string.tabText2
        )
    }
}