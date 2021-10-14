package com.example.room.presentation.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.room.R
import com.example.room.databinding.FragmentWriteBinding
import com.example.room.util.getToday
import com.example.room.util.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WriteFragment : Fragment() {
    private var _binding: FragmentWriteBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val writeViewModel by viewModels<WriteViewModel>()
    private val args by navArgs<WriteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTvDateText()
        setButtonText()
        setSaveClickListener()
        setDiaryCollect()
        setIsWrittenCollect()
        writeViewModel.getDetailDiary(args.idx)
    }

    private fun setTvDateText() {
        when (args.idx) {
            -1 -> binding.tvDate.text = getToday()
        }
    }

    private fun setButtonText() {
        binding.btnSaveDiary.text = when (args.idx) {
            -1 -> getString(R.string.save)
            else -> getString(R.string.update)
        }
    }

    private fun setSaveClickListener() {
        binding.btnSaveDiary.setOnClickListener {
            writeViewModel.writeOrUpdate(
                args.idx,
                binding.etTitle.text.toString(),
                binding.etContent.text.toString()
            )
        }
    }

    private fun setDiaryCollect() {
        repeatOnLifecycle {
            writeViewModel.diary.collect { diary ->
                binding.tvDate.text = diary.date
                binding.etTitle.setText(diary.title)
                binding.etContent.setText(diary.content)
            }
        }
    }

    private fun setIsWrittenCollect() {
        repeatOnLifecycle {
            writeViewModel.isWritten.collect { result ->
                when (result) {
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
