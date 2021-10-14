package com.example.room.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.room.R
import com.example.room.databinding.FragmentListBinding
import com.example.room.util.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val listViewModel by viewModels<ListViewModel>()
    private val diaryAdapter by lazy { DiaryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAddDiaryClickListener()
        setRvDiaryAdapter()
        setVerticalItemDecoration()
        setAllDiariesCollect()
    }

    private fun setAddDiaryClickListener() {
        binding.fabAddDiary.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToWriteFragment(
                -1,
                getString(R.string.write_label)
            )
            findNavController().navigate(action)
        }
    }

    private fun setRvDiaryAdapter() {
        binding.rvDiary.adapter = diaryAdapter
    }

    private fun setVerticalItemDecoration() {
        binding.rvDiary.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
    }

    private fun setAllDiariesCollect() {
        repeatOnLifecycle {
            listViewModel.getAllDiaries().collect {
                diaryAdapter.submitList(it)
            }
        }
    }
}
