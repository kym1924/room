package com.example.room.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.room.R
import com.example.room.databinding.FragmentListBinding
import com.example.room.util.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

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
        initClickListener()
        initRecyclerView()
        initCollect()
    }

    private fun initClickListener() {
        binding.fabAddDiary.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToWriteFragment(
                -1,
                getString(R.string.write_label)
            )
            findNavController().navigate(action)
        }
    }

    private fun initRecyclerView() {
        with(binding.rvDiary) {
            setHasFixedSize(true)
            adapter = diaryAdapter.withLoadStateHeaderAndFooter(
                header = DiaryLoadAdapter { diaryAdapter.retry() },
                footer = DiaryLoadAdapter { diaryAdapter.retry() }
            )
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    private fun initCollect() {
        repeatOnLifecycle {
            listViewModel.getAllDiaries().collectLatest {
                diaryAdapter.submitData(it)
            }
        }

        repeatOnLifecycle {
            diaryAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvDiary.scrollToPosition(0) }
        }

        repeatOnLifecycle {
            diaryAdapter.loadStateFlow.collectLatest { loadState ->
                val isDiaryEmpty =
                    loadState.refresh is LoadState.NotLoading && diaryAdapter.itemCount == 0
                binding.rvDiary.isVisible = !isDiaryEmpty
                binding.tvEmptyDiary.isVisible = isDiaryEmpty
                binding.loadingProgress.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }
    }
}
