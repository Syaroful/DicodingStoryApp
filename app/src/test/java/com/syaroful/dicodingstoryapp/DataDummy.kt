package com.syaroful.dicodingstoryapp

import com.syaroful.dicodingstoryapp.data.response.ListStoryItem

object DataDummy {
    fun generateDummyListStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                photoUrl = "",
                id = i.toString(),
                createdAt = "",
                name = "name $i",
                description = "desc $i",
                lon = i.toDouble(),
                lat = i.toDouble(),
            )
            items.add(story)
        }
        return items
    }
}