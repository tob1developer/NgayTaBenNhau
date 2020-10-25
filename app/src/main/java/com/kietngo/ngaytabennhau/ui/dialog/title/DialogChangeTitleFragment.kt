package com.kietngo.ngaytabennhau.ui.dialog.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentDialogChangeTitleBinding
import com.kietngo.ngaytabennhau.ui.fragment.home.HomeViewModel
import com.kietngo.ngaytabennhau.ui.option.OptionViewModel
import timber.log.Timber


class DialogChangeTitleFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogChangeTitleBinding
    private val viewModel : HomeViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogChangeTitleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val typeTitle = arguments?.getString("typeTitle")

        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }
        binding.btnSave.setOnClickListener {
            val top = "top"
            val bottom = "bottom"
            val title = binding.etChangeTitle.text.toString()
            if(typeTitle == top){
                viewModel.changeTopTitle(title)
                findNavController().navigateUp()
                Timber.d("change thanh cong top title")
            }
            else if(typeTitle == bottom){
                viewModel.changeBottomTitle(title)
                findNavController().navigateUp()
                Timber.d("change thanh cong bottom title")
            }
        }
    }
}