package com.example.room.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.room.R
import com.example.room.databinding.FragmentDetailBinding
import com.example.room.util.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val args by navArgs<DetailFragmentArgs>()
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setDiaryCollect()
        setIsDeletedCollect()
        detailViewModel.getDetailDiary(args.idx)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_update_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_update -> {
                val action = DetailFragmentDirections.actionDetailFragmentToWriteFragment(
                    args.idx,
                    getString(R.string.update_label)
                )
                findNavController().navigate(action)
                true
            }
            R.id.menu_delete -> {
                detailViewModel.deleteDiary(args.idx)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setDiaryCollect() {
        repeatOnLifecycle {
            detailViewModel.diary.collect { diary ->
                diary?.let {
                    binding.tvDetailTitle.text = diary.title
                    binding.tvDetailContent.text = diary.content
                }
            }
        }
    }

    private fun setIsDeletedCollect() {
        repeatOnLifecycle {
            detailViewModel.isDeleted.collect { isDeleted ->
                when (isDeleted) {
                    true -> findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
