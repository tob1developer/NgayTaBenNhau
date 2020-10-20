package com.kietngo.ngaytabennhau.ui.dialog.color

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.DialogFragmentColorBinding
import com.kietngo.ngaytabennhau.repository.model.Color
import com.kietngo.ngaytabennhau.ui.fragment.home.HomeViewModel
import com.kietngo.ngaytabennhau.ui.model.ColorUi
import timber.log.Timber


class ColorDialogFragment : DialogFragment() {
    private lateinit var binding : DialogFragmentColorBinding
    private lateinit var colorAdapter : ColorAdapter

    private val viewModel : HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentColorBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        colorAdapter = ColorAdapter()
//        colorAdapter.onNavigateUp = {
//            Timber.d("ddkdk $it")
//            viewModel.saveColorLove(it)
//            findNavController().navigateUp()
//        }
//
//        viewModel.listColor.observe(viewLifecycleOwner,{list ->
//            colorAdapter.submitList(list)
//        })
//
//        binding.listColor.apply {
//            adapter = colorAdapter
//            layoutManager =
//                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
//        }
//
//        binding.btnCancel.setOnClickListener {
//            findNavController().navigateUp()
//            Timber.d("back press color dialog to home")
//        }
//
//
//    }
}
