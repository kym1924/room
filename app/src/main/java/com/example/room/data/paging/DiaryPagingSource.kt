package com.example.room.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.room.data.entity.Diary
import com.example.room.data.room.DiaryDao

class DiaryPagingSource(
    private val dao: DiaryDao
) : PagingSource<Int, Diary>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Diary> {
        val position = params.key ?: 0
        return try {
            val response = dao.getAllDiaries(position)
            LoadResult.Page(
                data = response,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + (params.loadSize / 30)
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Diary>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
