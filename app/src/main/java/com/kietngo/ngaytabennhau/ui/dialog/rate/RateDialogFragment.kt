package com.kietngo.ngaytabennhau.ui.dialog.rate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentRateDialogBinding
import timber.log.Timber

class RateDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentRateDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRateDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCanel.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back rate dialog to setting")
        }
    }

}