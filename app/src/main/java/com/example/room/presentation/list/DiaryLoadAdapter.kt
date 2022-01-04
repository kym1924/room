package com.example.room.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.LoadingStateFooterBinding

class DiaryLoadAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<DiaryLoadAdapter.PagingLoadViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PagingLoadViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LoadingStateFooterBinding.inflate(inflater, parent, false)
        return PagingLoadViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: PagingLoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class PagingLoadViewHolder(
        private val binding: LoadingStateFooterBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.loadingProgress.isVisible = loadState is LoadState.Loading
            binding.tvNoSearchResult.isVisible = loadState is LoadState.Error
            binding.tvNoSearchResult.setOnClickListener {
                retry()
            }
        }
    }
}
