package com.syaroful.dicodingstoryapp.view.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syaroful.dicodingstoryapp.data.response.ListStoryItem
import com.syaroful.dicodingstoryapp.databinding.StoryCardBinding
import com.syaroful.dicodingstoryapp.utils.withDateFormat
import com.syaroful.dicodingstoryapp.view.detail.DetailActivity

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.MyViewHolder>() {
    private var stories: List<ListStoryItem> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<ListStoryItem>) {
        stories = newList
        notifyDataSetChanged()
    }

    class MyViewHolder(private val binding: StoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stories: ListStoryItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(stories.photoUrl)
                    .into(ivStory)
                tvName.text = stories.name
                tvDate.text = stories.createdAt?.withDateFormat()
                tvDescription.text = stories.description
            }
            itemView.setOnClickListener {
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivStory, "image"),
                        Pair(binding.tvName, "name"),
                        Pair(binding.tvDescription, "description")
                    )
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, stories)
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = StoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(stories[position])
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }


}