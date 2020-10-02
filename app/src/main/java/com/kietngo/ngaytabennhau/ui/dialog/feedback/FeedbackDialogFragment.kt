package com.kietngo.ngaytabennhau.ui.dialog.feedback

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentFeedbackDialogBinding
import com.kietngo.ngaytabennhau.repository.EventObserver
import com.kietngo.ngaytabennhau.ui.fragment.setting.SettingViewModel
import timber.log.Timber

class FeedbackDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentFeedbackDialogBinding
    private val viewModel : SettingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back press feedback dialog to setting")
        }

        viewModel.btnSendContentToEmail.observe(viewLifecycleOwner, { btn ->
            binding.btnFeedback.setOnClickListener {
                btn.onClick()
                Timber.d("go to intent email")
            }
        })

        viewModel.navigateSendEmail.observe(viewLifecycleOwner, EventObserver{
            val content = binding.editTextFeedback.text.toString()
            val email : Array<String> = Array(1){"tuankiet281299@gmail.com"}
            val subject = "Send Feedback to the app"
            if (it && content != ""){
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    type
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, email)
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, content)
                }
                startActivity(Intent.createChooser(emailIntent, "send email"))
            }
        })
    }


}