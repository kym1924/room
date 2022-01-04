package com.example.room.presentation.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.room.data.entity.Diary
import com.example.room.databinding.ItemDiaryBinding

class DiaryAdapter : PagingDataAdapter<Diary, DiaryAdapter.DiaryViewHolder>(diaryDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDiaryBinding.inflate(inflater, parent, false)
        return DiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class DiaryViewHolder(
        private var binding: ItemDiaryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: Diary) {
            Log.d("kym1924", diary.toString())
            binding.diary = diary
            binding.root.setOnClickListener {
                val action =
                    ListFragmentDirections.actionListFragmentToDetailFragment(diary.idx, diary.date)
                it.findNavController().navigate(action)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        private val diaryDiffUtil = object : DiffUtil.ItemCallback<Diary>() {
            override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
                return oldItem.idx == newItem.idx
            }

            override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
                return oldItem == newItem
            }
        }
    }
}
