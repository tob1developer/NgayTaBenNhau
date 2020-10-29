package com.kietngo.ngaytabennhau.ui.fragment.setting

import android.app.Notification
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentSettingBinding
import com.kietngo.ngaytabennhau.repository.EventObserver
import com.kietngo.ngaytabennhau.repository.TURN_OFF_NOTIFICATION
import com.kietngo.ngaytabennhau.repository.TURN_ON_NOTIFICATION
import com.kietngo.ngaytabennhau.ui.notification.NotificationLove
import kotlinx.android.synthetic.main.layout_notification.view.*
import timber.log.Timber

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SettingViewModel by activityViewModels(){
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.application?.let { SettingViewModel(it) } as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val notification = NotificationLove(requireContext(),requireActivity())


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back to home")
        }
        viewModel.btnFeedback.observe(viewLifecycleOwner, { btn ->
            binding.btnFeedback.setOnClickListener { btn.onClick() }
        })

        //TODO: Update User to notification
        viewModel.user1.observe(viewLifecycleOwner, {user1 ->
           notification.updateUser(user1, R.id.nameUser1, R.id.tvDateOfBirth1)
        })

        viewModel.user2.observe(viewLifecycleOwner, {user2 ->
            notification.updateUser(user2, R.id.nameUser2, R.id.tvDateOfBirth2)
        })

        viewModel.loveDate.observe(viewLifecycleOwner, {loveDate ->
            notification.updateDay(loveDate)
        })


        viewModel.loveDate.observe(viewLifecycleOwner, {loveDate ->
            val status = loveDate.statusNotification

            Timber.d("check status $status")

            if(status == TURN_ON_NOTIFICATION){
                binding.switchNotification.isChecked = true
                notification.turnOnNotification()
            }
            else if(status == TURN_OFF_NOTIFICATION){
                binding.switchNotification.isChecked = false
                notification.cancelNotification()
            }
        })

        binding.switchNotification.setOnClickListener {

            if (binding.switchNotification.isChecked){
                viewModel.changeStatusNotification(TURN_ON_NOTIFICATION)
            }
            else{
               viewModel.changeStatusNotification(TURN_OFF_NOTIFICATION)
            }
        }

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