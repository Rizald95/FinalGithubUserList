package com.example.finalgithubuserlist.ui.follow

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.finalgithubuserlist.R
import com.example.finalgithubuserlist.databinding.ActivityMainBinding
import com.example.finalgithubuserlist.databinding.FragmentFollowBinding
import com.example.finalgithubuserlist.helper.SettingPreferences
import com.example.finalgithubuserlist.helper.ViewModelFactory
import com.example.finalgithubuserlist.ui.main.MainViewModel
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalgithubuserlist.ui.adapter.UserAdapter


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SettingPreferences.PREFERENCES_KEY.THEME_KEY_SETTINGS)

     private val viewModel by viewModels<FollowViewModel>()

    private var position: Int = 0
    private var username: String? = null
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArgumentFragment()
        setupRecyclerViewFragment()

        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {isLoading -> isLoading(isLoading)}

        }

        if (position == 1) {
            if (savedInstanceState == null) {
                viewModel.getFollowersGithubUsername(username.toString())

            }
            viewModel.followers.observe(viewLifecycleOwner) {followers -> adapter.submitList(followers)}
        } else {
            if (savedInstanceState == null) {
                viewModel.getFollowingGithubUsername(username.toString())
            }
            viewModel.following.observe(viewLifecycleOwner) { following -> adapter.submitList(following)}
        }
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerFollows.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onPause() {
        super.onPause()
        binding.recyclerFollows.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0
        )
    }


    private fun setupArgumentFragment() {
        arguments?.let {
            position = it.getInt(ARGUMENT_POSITION)
            username = it.getString(ARGUMENT_USERNAME)
        }
    }

    private fun setupRecyclerViewFragment() {
        adapter = UserAdapter()
        binding.recyclerFollows.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@FollowFragment.adapter
        }
    }

   

    private fun isLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARGUMENT_POSITION = "position"
        const val ARGUMENT_USERNAME = "username"
    }



}