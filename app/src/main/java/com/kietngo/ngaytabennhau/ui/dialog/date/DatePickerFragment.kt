package com.kietngo.ngaytabennhau.ui.dialog.date

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.kietngo.ngaytabennhau.ui.fragment.profile.ProfileViewModel
import java.util.*

class DatePickerFragment(): DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel: ProfileViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext() , this, year, month, day)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, p3)
        calendar.set(Calendar.MONTH, p2)
        calendar.set(Calendar.YEAR, p1)
        // get calendar to dialog
        viewModel.getCalendar.postValue(calendar)
    }
}