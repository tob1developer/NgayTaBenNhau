package com.kietngo.ngaytabennhau.ui.option

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.R
import com.kietngo.ngaytabennhau.databinding.FragmentDialogOptionBinding
import com.kietngo.ngaytabennhau.repository.EventObserver
import com.kietngo.ngaytabennhau.ui.fragment.home.HomeViewModel
import timber.log.Timber
import java.io.InputStream

const val SELECT_IMG_REQ_CODE = 2
class DialogOptionFragment : DialogFragment() {

    lateinit var binding: FragmentDialogOptionBinding
    private val viewModel : OptionViewModel by activityViewModels(){
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.application?.let { OptionViewModel(it) } as T
            }
        }
    }
    private val homeViewModel : HomeViewModel by activityViewModels()

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

        //TODO: change wallpage
        viewModel.btnChangeWallPage.observe(viewLifecycleOwner,{btn ->
            binding.btnWallPaper.setOnClickListener {
                btn.onClick()
            }
        })

        viewModel.navigateChangeWallPage.observe(viewLifecycleOwner, EventObserver{
            if (it) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intent, "Select Picture Wallpage"),
                    SELECT_IMG_REQ_CODE
                )
            }
            findNavController().navigateUp()
        })

        //TODO: change date
        viewModel.btnChangeDate.observe(viewLifecycleOwner, {btn->
            binding.btnChangeDate.setOnClickListener {
                btn.onClick()
            }
        })

        viewModel.navigateChangeDate.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigateUp()
            findNavController().navigate(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SELECT_IMG_REQ_CODE && data != null) {
            val inputStream : InputStream? = data.data?.let {
                requireContext().contentResolver.openInputStream(it)
            }
            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
            homeViewModel.changeWallPage(bitmap)
            Timber.d("change background")
            // dang bi loi
        }

    }
}