package com.kietngo.ngaytabennhau.ui.fragment.date

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.kietngo.ngaytabennhau.repository.AppDatabase
import com.kietngo.ngaytabennhau.repository.Event
import com.kietngo.ngaytabennhau.repository.ID_LOVE_DATE
import com.kietngo.ngaytabennhau.repository.NAME_DATABASE
import com.kietngo.ngaytabennhau.ui.model.ButtonUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateViewModel constructor(
    private val applicationContext: Context
) : ViewModel(){

    private var database : AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, NAME_DATABASE)
            .build()

    private val _btnChangeDate = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                _navigateDatePicker.postValue(Event(true))
            }
        )
    }
    val btnChangeDate : LiveData<ButtonUi> = _btnChangeDate

    private val _navigateDatePicker = MutableLiveData<Event<Boolean>>()
    val navigateDatePicker : LiveData<Event<Boolean>> = _navigateDatePicker

    val getCalendarFirst : MutableLiveData<Calendar> by lazy {
        MutableLiveData<Calendar>()
    }

    val getCalendarPresent : MutableLiveData<Calendar> by lazy {
        MutableLiveData<Calendar>()
    }

    val countDateBeenTogether : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        //khoi tao date Present
        val calendarPresent = Calendar.getInstance()
        getCalendarPresent.value = calendarPresent

        viewModelScope.launch(Dispatchers.IO) {
            val dateLove = database.loveDateDao().loadLoveDate(ID_LOVE_DATE)

            withContext(Dispatchers.Main){
                getCalendarFirst.value = dateLove.startDate
                setDay()
            }
        }
    }

    fun setDay(){
        val time  = getCalendarPresent.value?.time?.time?.minus(getCalendarFirst.value?.time?.time!!)
        val time1Day = 1000*60*60*24
        val time1Hour = 1000*60*60

        val day : Long? = time?.div(time1Day)
        val hour: Long? = (day?.times(time1Day)?.let { time?.minus(it) })?.div(time1Hour)
        val showDay = " $day day - $hour hour"
        countDateBeenTogether.postValue(showDay)
    }

    fun saveStartDateTogether(calendar: Calendar){
        viewModelScope.launch(Dispatchers.IO){
            var dateLove = database.loveDateDao().loadLoveDate(ID_LOVE_DATE)

                dateLove.startDate = calendar

                database.loveDateDao().updateLoveDate(dateLove)
                Timber.d("update day together")

                var dateLoveTest = database.loveDateDao().loadLoveDate(ID_LOVE_DATE)
        }
    }

}