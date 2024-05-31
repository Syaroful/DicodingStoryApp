package com.syaroful.dicodingstoryapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.syaroful.dicodingstoryapp.databinding.ActivityMainBinding
import com.syaroful.dicodingstoryapp.view.ViewModelFactory
import com.syaroful.dicodingstoryapp.view.create_story.CreateStoryActivity
import com.syaroful.dicodingstoryapp.view.maps.MapsActivity
import com.syaroful.dicodingstoryapp.view.onboarding.OnboardingActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoryAdapter
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            } else {
                binding.toolbarUserEmail.text = user.email
            }
        }
        setupView()
        setupAction()
    }

    override fun onResume() {
        super.onResume()
        if(!adapter.snapshot().isEmpty()){
            adapter.refresh()
            lifecycleScope.launch {
                adapter.loadStateFlow
                    .collect {
                        binding.rvStory.smoothScrollToPosition(0)
                    }
            }
        }
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)
        getStories()
    }


    private fun setupAction() {

        binding.logoutButton.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Log out!")
                setMessage("Are you sure you want to logout?")
                setPositiveButton("Log out") { _, _ ->
                    viewModel.logout()
                }
                setNegativeButton("No", null)
                create()
                show()
            }

        }

        binding.addStoryButton.setOnClickListener {
            startActivity(Intent(this, CreateStoryActivity::class.java))
        }

        binding.mapsButton.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    private fun getStories() {
        adapter = StoryAdapter()
        binding.rvStory.adapter = adapter
        viewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}