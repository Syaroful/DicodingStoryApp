package com.syaroful.dicodingstoryapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.syaroful.dicodingstoryapp.data.api.ApiService
import com.syaroful.dicodingstoryapp.data.response.ListStoryItem

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseItem = apiService.getStories(position, params.loadSize)
            val item = responseItem.listStory
            LoadResult.Page(
                data = item,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseItem.listStory.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}