package com.syaroful.dicodingstoryapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.syaroful.dicodingstoryapp.R
import com.syaroful.dicodingstoryapp.data.ResultState
import com.syaroful.dicodingstoryapp.databinding.ActivityMainBinding
import com.syaroful.dicodingstoryapp.view.ViewModelFactory
import com.syaroful.dicodingstoryapp.view.create_story.CreateStoryActivity
import com.syaroful.dicodingstoryapp.view.detail.DetailActivity
import com.syaroful.dicodingstoryapp.view.onboarding.OnboardingActivity

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

        adapter = StoryAdapter()
        setupView()
        setupAction()
    }

    private fun setupView() {
        binding.rvStory.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        binding.addStoryButton.setBackgroundColor(getColor(R.color.yellow500))
    }

    private fun setupAction() {
        viewModel.getSession()
        viewModel.getStories().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        if (result.data.isEmpty()) {
                            Toast.makeText(this, "empty data", Toast.LENGTH_SHORT).show()
                        } else {
                            adapter.submitList(result.data)
                        }
                    }
                }
            }
        }

        adapter.setOnItemListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, it)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, OnboardingActivity::class.java))
        }

        binding.addStoryButton.setOnClickListener {
            startActivity(Intent(this, CreateStoryActivity::class.java))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}