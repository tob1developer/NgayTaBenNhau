package com.kietngo.ngaytabennhau.ui.dialog.profire

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.DialogFragmentProfileBinding
import com.kietngo.ngaytabennhau.repository.*
import com.kietngo.ngaytabennhau.repository.model.User
import com.kietngo.ngaytabennhau.ui.dialog.date.DatePickerFragment
import com.kietngo.ngaytabennhau.ui.fragment.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import timber.log.Timber
import java.io.InputStream
import java.util.*

const val SELECT_IMG_REQ_CODE = 1

class ProfileDialogFragment : DialogFragment() {
    private lateinit var binding : DialogFragmentProfileBinding
    private val viewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var userToSave : User? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.textViewName.isEnabled = false

        viewModel.getUserInProfileDialog.observe(viewLifecycleOwner, {
            binding.textViewName.setText(it.nickName)
            binding.textViewDate.text = it.birthDay
            binding.imageViewAvatar.setImageBitmap(it.avatar)
            binding.imageViewAvatar.borderColor = Color.parseColor(it.borderColor)
            viewGenderToDatabase(it.gender)
            userToSave = it
        })


        viewModel.btnChangeAvatar.observe(viewLifecycleOwner, { btn ->
            binding.btnChangeAvatar.setOnClickListener {
                btn.onClick()
                Timber.d("go to gallery android")
            }
        })

        viewModel.btnSaveProfile.observe(viewLifecycleOwner,{btn ->
            binding.btnSave.setOnClickListener {
                btn.onClick()
                Timber.d("navigateUp Profile to Home")
            }
        })

        viewModel.btnSetNameUser.observe(viewLifecycleOwner, {btn ->
            binding.btnChangeName.setOnClickListener {
                btn.onClick()
                Timber.d("Enable edit name")
            }
        })

        viewModel.btnChangeDate.observe(viewLifecycleOwner,{btn ->
            binding.btnChangeDate.setOnClickListener {
                btn.onClick()
                Timber.d("turn on Date Picker Dialog")
            }
        })

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back press dialog to home ")
        }

        viewModel.navigateToGallery.observe(viewLifecycleOwner, EventObserver{
            if (it) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMG_REQ_CODE)
            }
        })

        viewModel.navigateSaveAndToBackHome.observe(viewLifecycleOwner, EventObserver{ checker ->
            if (checker){
                userToSave?.avatar = binding.imageViewAvatar.drawable.toBitmap()
                userToSave?.nickName = binding.textViewName.text.toString()
                userToSave?.birthDay = binding.textViewDate.text.toString()
                userToSave?.gender = getGender()

                userToSave?.let { viewModel.saveUserToDb(it) }

                val action =
                    ProfileDialogFragmentDirections.actionProfileDialogFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        })


        viewModel.changeNameUser.observe(viewLifecycleOwner, EventObserver{
            if (it) {
                binding.textViewName.isEnabled = true
            }
        })

        viewModel.navigateDatePicker.observe(viewLifecycleOwner, EventObserver{
            if (it){
                val action = ProfileDialogFragmentDirections.actionProfileDialogFragmentToDatePickerFragment()
                findNavController().navigate(action)
            }
        })

        val calendar = Observer<Calendar> {
            val day = it.get(Calendar.DAY_OF_MONTH)
            val month = it.get(Calendar.MONTH) + 1
            val year = it.get(Calendar.YEAR)
            val calendarToString = "$day/$month/$year"
            binding.textViewDate.text = calendarToString
        }

        viewModel.getCalendar.observe(this, calendar)
    }

    private fun viewGenderToDatabase(gender : Int){
        when(gender){
            GENDER_MALE -> binding.radioMale.isChecked = true
            GENDER_FEMALE -> binding.radioFemale.isChecked = true
            GENDER_GAY -> binding.radioGay.isChecked = true
            GENDER_LESS -> binding.radioLes.isChecked = true
        }
    }

    private fun getGender() : Int{
        var genderSet = NO_GENDER
        when(binding.btnChangGender.checkedRadioButtonId){
            R.id.radio_male -> genderSet = GENDER_MALE
            R.id.radio_female -> genderSet = GENDER_FEMALE
            R.id.radio_gay -> genderSet = GENDER_GAY
            R.id.radio_les -> genderSet = GENDER_LESS
        }

        Timber.d("get gender to user")
        return genderSet
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SELECT_IMG_REQ_CODE && data != null) {
            val inputStream : InputStream? = data.data?.let {
                requireContext().contentResolver.openInputStream(it)
            }
            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

            //update image to dialog
            binding.imageViewAvatar.setImageBitmap(bitmap)
        }
    }


}