package com.example.finalgithubuserlist.ui.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.finalgithubuserlist.R
import com.example.finalgithubuserlist.databinding.ActivitySettingBinding
import com.example.finalgithubuserlist.helper.SettingPreferences
import com.example.finalgithubuserlist.helper.ViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private val settingViewModel by viewModels<SettingViewModel> {
        ViewModelFactory.getInstance(application, SettingPreferences.getInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = binding.switchTheme

        settingViewModel.getThemeSettings().observe(this) {
                isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }

        }
        switchTheme.setOnCheckedChangeListener {
                _: CompoundButton?, isChecked: Boolean -> settingViewModel.saveThemeSetting(isChecked)
        }

    }
}