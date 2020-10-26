package com.kietngo.ngaytabennhau.ui.dialog.color

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentColorBorderDialogBinding
import com.kietngo.ngaytabennhau.repository.ID_USER_1
import com.kietngo.ngaytabennhau.ui.fragment.profile.ProfileDialogFragment
import com.kietngo.ngaytabennhau.ui.fragment.profile.ProfileViewModel
import timber.log.Timber


class ColorBorderDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentColorBorderDialogBinding
    private lateinit var colorAdapter : ColorAdapter
    private val viewModel : ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorBorderDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val idUser = arguments?.getInt("idUserToBorder")

        Timber.d("check id $idUser")
        colorAdapter = ColorAdapter()
        colorAdapter.onNavigateUp = {
            if (idUser != null){
                viewModel.saveColorLove(it,idUser)
            }else{
                Toast.makeText(requireContext(),"Lỗi không thể thay đổi!",Toast.LENGTH_SHORT).show()
            }
            findNavController().navigateUp()
        }

        viewModel.listColor.observe(viewLifecycleOwner,{list ->
            colorAdapter.submitList(list)
        })

        binding.listColor.apply {
            adapter = colorAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back press color dialog to home")
        }


    }


}