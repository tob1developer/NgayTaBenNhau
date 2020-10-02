package com.kietngo.ngaytabennhau.ui.fragment.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentSettingBinding
import com.kietngo.ngaytabennhau.repository.EventObserver
import timber.log.Timber

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SettingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back to home")
        }
        viewModel.btnFeedback.observe(viewLifecycleOwner, { btn ->
            binding.btnFeedback.setOnClickListener { btn.onClick() }
        })

        viewModel.navigateFeedbackDialog.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(it)
        })

        viewModel.btnFanpage.observe(viewLifecycleOwner, { btn ->
            binding.btnFanpage.setOnClickListener {
                btn.onClick()
                Timber.d("go to fanpage")
            }
        })
        viewModel.navigateSendEmail.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                val url = "https://www.facebook.com/profile.php?id=100006074898747"
                val intentView = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(intentView)
            }
        })

        viewModel.btnRate.observe(viewLifecycleOwner, {btn ->
            binding.btnRate.setOnClickListener {
                btn.onClick()
                Timber.d("go to rate dialog")
            }
        })

        viewModel.navigateRateDialog.observe(viewLifecycleOwner,EventObserver{
            findNavController().navigate(it)
        })
    }
}