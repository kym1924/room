package com.example.room.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.data.entity.Diary
import com.example.room.databinding.ItemDiaryBinding

class DiaryAdapter : ListAdapter<Diary, DiaryAdapter.DiaryViewHolder>(diaryDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DiaryViewHolder(
            ItemDiaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiaryViewHolder(
        private var binding: ItemDiaryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: Diary) {
            binding.diary = diary
            binding.executePendingBindings()
            setLayoutClickListener(diary.idx, diary.date)
        }

        private fun setLayoutClickListener(idx: Int, date: String) {
            binding.root.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToDetailFragment(idx, date)
                it.findNavController().navigate(action)
            }
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
