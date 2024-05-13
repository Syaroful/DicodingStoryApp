package com.syaroful.dicodingstoryapp.view.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.syaroful.dicodingstoryapp.data.response.ListStoryItem
import com.syaroful.dicodingstoryapp.databinding.ActivityDetailBinding
import com.syaroful.dicodingstoryapp.view.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    @Suppress("DEPRECATION")
    private fun setupView() {
        val data = intent.getParcelableExtra<ListStoryItem>(EXTRA_DATA)
        if (data != null) {
            binding.apply {
                tvName.text = data.name
                tvStoryDesc.text = data.description
                Glide.with(this@DetailActivity)
                    .load(data.photoUrl)
                    .into(ivDetailStory)
            }
        } else {
            showToast("Data doesn't Exist")
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}