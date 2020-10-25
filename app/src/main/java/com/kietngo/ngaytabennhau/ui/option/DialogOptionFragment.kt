package com.kietngo.ngaytabennhau.ui.option

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentDialogOptionBinding
import com.kietngo.ngaytabennhau.repository.EventObserver

class DialogOptionFragment : DialogFragment() {

    lateinit var binding: FragmentDialogOptionBinding
    private val viewModel : OptionViewModel by activityViewModels(){
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.application?.let { OptionViewModel(it) } as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //TODO: go to change top title
        viewModel.btnChangeTopTitle.observe(viewLifecycleOwner, {btn ->
            binding.btnChangeTopTitle.setOnClickListener {
                btn.onClick()

            }
        })

        //TODO: go to change bottom title
        viewModel.btnChangeBottomTitle.observe(viewLifecycleOwner, {btn ->
            binding.btnChangeBottomTitle.setOnClickListener {
                btn.onClick()
            }
        })

        viewModel.navigateChangeTitle.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigateUp()
            findNavController().navigate(it)

        })
    }
}