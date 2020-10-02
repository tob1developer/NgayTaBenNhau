package com.kietngo.ngaytabennhau.ui.fragment.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kietngo.ngaytabennhau.databinding.FragmentDateBinding
import com.kietngo.ngaytabennhau.repository.EventObserver
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class DateFragment : Fragment() {
    private lateinit var binding: FragmentDateBinding
    private val viewModel : DateViewModel by activityViewModels(){
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return DateViewModel(requireContext()) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
            Timber.d("back press Date to Home.")
        }
        viewModel.btnChangeDate.observe(viewLifecycleOwner, { btn ->
            binding.btnChangeDateFirst.setOnClickListener {
                btn.onClick()
            }
        })

        viewModel.navigateDatePicker.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                val action = DateFragmentDirections.actionDateFragmentToDatePickerTogetherFragment()
                findNavController().navigate(action)
            }
        })

        val calendarFirst = Observer<Calendar> {
            val day = it.get(Calendar.DAY_OF_MONTH)
            val month = it.get(Calendar.MONTH)
            val year = it.get(Calendar.YEAR)
            val calendarToString = "$day/$month/$year"
            binding.dateFirstLove.text = calendarToString
        }

        val calendarPresent = Observer<Calendar> {
            val day = it.get(Calendar.DAY_OF_MONTH)
            val month = it.get(Calendar.MONTH) + 1
            val year = it.get(Calendar.YEAR)
            val calendarToString = "$day/$month/$year"
            binding.datePresent.text = calendarToString
        }

        val day = Observer<String> {
            if (it != null){
                binding.TimeTogether.text = it
            }
        }

        viewModel.getCalendarFirst.observe(viewLifecycleOwner, calendarFirst)
        viewModel.getCalendarPresent.observe(viewLifecycleOwner, calendarPresent)
        viewModel.countDateBeenTogether.observe(viewLifecycleOwner, day)

    }

}