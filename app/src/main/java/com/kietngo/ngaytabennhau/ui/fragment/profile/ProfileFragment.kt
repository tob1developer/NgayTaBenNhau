package com.kietngo.ngaytabennhau.ui.fragment.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentProfileBinding
import com.kietngo.ngaytabennhau.repository.*
import com.kietngo.ngaytabennhau.repository.model.User
import timber.log.Timber
import java.io.InputStream
import java.util.*


const val SELECT_IMG_REQ_CODE = 1

class ProfileDialogFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private val viewModel : ProfileViewModel by activityViewModels(){
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.application?.let { ProfileViewModel(it) } as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.textViewName.isEnabled = false

        val idUser = arguments?.getInt("idUser")
        var user : LiveData<User>? = null
        if(idUser == 1)  user = viewModel.user1
        else if(idUser == 2) user = viewModel.user2

        user?.observe(viewLifecycleOwner, { user ->
            binding.textViewName.setText(user.nickName)
            binding.textViewDate.text = user.birthDay
            binding.imageViewAvatar.setImageBitmap(user.avatar)
            binding.imageViewAvatar.borderColor = Color.parseColor(user.borderColor)
            Timber.d("check color ${user.borderColor}")
            viewGenderToDatabase(user.gender)
        })

        //TODO: btn change Border Color
        viewModel.btnChangBorder.observe(viewLifecycleOwner, {btn ->
            binding.btnChangeBorder.setOnClickListener {
                btn.onClick()
            }
        })

        viewModel.navigateChangeBorder.observe(viewLifecycleOwner, EventObserver{
            if(it){
                if(idUser != null){
                    val action = ProfileDialogFragmentDirections
                        .actionProfileDialogFragmentToColorBorderDialogFragment(idUser)
                    findNavController().navigate(action)

                }else{
                    Toast.makeText(requireContext(),
                        "Lỗi không thể sang màn hình thay đổi color!",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        // TODO: Btn change Avatar
        viewModel.btnChangeAvatar.observe(viewLifecycleOwner, { btn ->
            binding.btnChangeAvatar.setOnClickListener {
                btn.onClick()
                Timber.d("go to gallery android")
            }
        })

        viewModel.navigateToGallery.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    SELECT_IMG_REQ_CODE
                )
            }
        })

        //TODO: Save profile
        viewModel.btnSaveProfile.observe(viewLifecycleOwner, { btn ->
            binding.btnSave.setOnClickListener {
                btn.onClick()
                Timber.d("navigateUp Profile to Home")
            }
        })

        viewModel.navigateSaveAndToBackHome.observe(viewLifecycleOwner, EventObserver { checker ->
            if (checker && id != null) {
                val avatar = binding.imageViewAvatar.drawable.toBitmap()
                val nickName = binding.textViewName.text.toString()
                val birthDay = binding.textViewDate.text.toString()
                val gender = getGender()

                val userSave = User(id, avatar, nickName, birthDay, gender, "#FFFFFF")

                if (user?.value == userSave) {
                    Toast.makeText(requireContext(), "Không có gì thay đổi!", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigateUp()
                } else {
                    viewModel.saveProfileUser(userSave)
                    val action =
                        ProfileDialogFragmentDirections.actionProfileDialogFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            }
        })

        //TODO: change name User
        viewModel.btnSetNameUser.observe(viewLifecycleOwner, { btn ->
            binding.btnChangeName.setOnClickListener {
                btn.onClick()
                Timber.d("Enable edit name")
            }
        })
        viewModel.changeNameUser.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                binding.textViewName.isEnabled = true
                binding.textViewName.requestFocus();

                val position = binding.textViewName.text.toString().length
                binding.textViewName.setSelection(position)

                binding.textViewName.isFocusable = true

               val im = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE)
                       as InputMethodManager
                im.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            }
        })

        //TODO: Change Date
        viewModel.btnChangeDate.observe(viewLifecycleOwner, { btn ->
            binding.btnChangeDate.setOnClickListener {
                btn.onClick()
                Timber.d("turn on Date Picker Dialog")
            }
        })

        viewModel.navigateDatePicker.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                val action =
                    ProfileDialogFragmentDirections.actionProfileDialogFragmentToDatePickerFragment()
                findNavController().navigate(action)
            }
        })

        //TODO: Cancel and back to Home Fragment
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back press dialog to home ")
        }

        //TODO: get calendar
        val calendar = Observer<Calendar> {
            val day = it.get(Calendar.DAY_OF_MONTH)
            val month = it.get(Calendar.MONTH) + 1
            val year = it.get(Calendar.YEAR)
            val calendarToString = "$day/$month/$year"
            binding.textViewDate.text = calendarToString
        }

        viewModel.getCalendar.observe(viewLifecycleOwner, calendar)
    }

    private fun viewGenderToDatabase(gender: Int){
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