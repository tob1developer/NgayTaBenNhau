package com.kietngo.ngaytabennhau.ui.option

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.kietngo.ngaytabennhau.repository.AppDatabase
import com.kietngo.ngaytabennhau.repository.Event
import com.kietngo.ngaytabennhau.repository.model.LoveDate
import com.kietngo.ngaytabennhau.repository.repository.LoveDateRepository
import com.kietngo.ngaytabennhau.ui.fragment.home.HomeFragmentDirections
import com.kietngo.ngaytabennhau.ui.model.ButtonUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class OptionViewModel constructor(
    application: Application
): ViewModel(){
    private val loveDateRepository : LoveDateRepository
    val loveDate : LiveData<LoveDate>

    init {
        val loveDateDao = AppDatabase.getDatabase(application, viewModelScope).loveDateDao()
        loveDateRepository = LoveDateRepository(loveDateDao)
        loveDate = loveDateRepository.loveDate
    }

    //TODO: btn Change Top title
    private val _btnChangeTopTitle =
        MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                val typeTitle = "top"
                val action = HomeFragmentDirections
                    .actionHomeFragmentToDialogChangeTitleFragment(typeTitle)

                _navigateChangeTitle.postValue(Event(action))
                Timber.d("navigate change Top title")
            }
        )
    }
    val btnChangeTopTitle : LiveData<ButtonUi> = _btnChangeTopTitle


    //TODO: btn Change Bottom Title
    private val _btnChangeBottomTitle =
        MutableLiveData<ButtonUi>().apply {
            value = ButtonUi(
                onClick = {
                    val typeTitle = "bottom"
                    val action = HomeFragmentDirections
                        .actionHomeFragmentToDialogChangeTitleFragment(typeTitle)

                    _navigateChangeTitle.postValue(Event(action))
                    Timber.d("navigate Change bottom title")
                }
            )
        }
    val btnChangeBottomTitle : LiveData<ButtonUi> = _btnChangeBottomTitle

    private val _navigateChangeTitle = MutableLiveData<Event<NavDirections>>()
    val navigateChangeTitle : LiveData<Event<NavDirections>> = _navigateChangeTitle

    //TODO: btn Change Wall Page
    private val _btnChangeWallPage =
        MutableLiveData<ButtonUi>().apply {
            value = ButtonUi(
                onClick = {
                    _navigateChangeWallPage.postValue(Event(true))
                }
            )
        }
    val btnChangeWallPage : LiveData<ButtonUi> = _btnChangeWallPage

    private val _navigateChangeWallPage = MutableLiveData<Event<Boolean>>()
    val navigateChangeWallPage : LiveData<Event<Boolean>> = _navigateChangeWallPage

    //TODO: btn change Date
    private val _btnChangeChangeDate =
        MutableLiveData<ButtonUi>().apply {
            value = ButtonUi(
                onClick = {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToDatePickerTogetherFragment()


                    _navigateChangeDate.postValue(Event(action))
                    Timber.d("navigate to date fragment")
                }
            )
        }
    val btnChangeDate : LiveData<ButtonUi> = _btnChangeChangeDate

    private val _navigateChangeDate = MutableLiveData<Event<NavDirections>>()
    val navigateChangeDate : LiveData<Event<NavDirections>> = _navigateChangeDate

}