package com.kietngo.ngaytabennhau.ui.fragment.setting

import android.app.Application
import android.util.EventLogTags
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.kietngo.ngaytabennhau.repository.AppDatabase
import com.kietngo.ngaytabennhau.repository.Event
import com.kietngo.ngaytabennhau.repository.model.LoveDate
import com.kietngo.ngaytabennhau.repository.model.User
import com.kietngo.ngaytabennhau.repository.repository.LoveDateRepository
import com.kietngo.ngaytabennhau.repository.repository.UserRepository
import com.kietngo.ngaytabennhau.ui.model.ButtonUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel constructor(
    private val application: Application
): ViewModel(){

    private val loveDateRepository: LoveDateRepository
    val loveDate : LiveData<LoveDate>

    private val userRepository: UserRepository
    val user1 : LiveData<User>
    val user2 : LiveData<User>

    init {
        val loveDateDao = AppDatabase.getDatabase(application, viewModelScope).loveDateDao()
        loveDateRepository = LoveDateRepository(loveDateDao)
        loveDate = loveDateRepository.loveDate

        val userDao = AppDatabase.getDatabase(application,viewModelScope).userDao()
        userRepository = UserRepository(userDao)

        user1 = userRepository.user1
        user2 = userRepository.user2
    }

    private val _btnFeedback = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick ={
                val action = SettingFragmentDirections.actionSettingFragmentToFeedbackDialogFragment()
                _navigateFeedbackDialog.postValue(Event(action))
            }
        )
    }
    val btnFeedback : LiveData<ButtonUi> = _btnFeedback

    private val _navigateFeedbackDialog = MutableLiveData<Event<NavDirections>>()
    val navigateFeedbackDialog : LiveData<Event<NavDirections>> = _navigateFeedbackDialog


    private val _btnFanpage = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                _navigateSendEmail.postValue(Event(true))
            }
        )
    }
    val btnFanpage : LiveData<ButtonUi> = _btnFanpage

    private val _navigateFanpage = MutableLiveData<Event<Boolean>>()
    val navigateFanpage : LiveData<Event<Boolean>> = _navigateFanpage

    private val _btnSendContentToEmail = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                _navigateSendEmail.postValue(Event(true))
            }
        )
    }
    val btnSendContentToEmail : LiveData<ButtonUi> = _btnSendContentToEmail

    private val _navigateSendEmail = MutableLiveData<Event<Boolean>>()
    val navigateSendEmail : LiveData<Event<Boolean>> = _navigateSendEmail


    private val _btnRate = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                val action = SettingFragmentDirections.actionSettingFragmentToRateDialogFragment()
                _navigateRateDialog.postValue(Event(action))
            }
        )
    }
    val btnRate : LiveData<ButtonUi> = _btnRate

    private val _navigateRateDialog = MutableLiveData<Event<NavDirections>>()
    val navigateRateDialog : LiveData<Event<NavDirections>> = _navigateRateDialog

    fun changeStatusNotification(key: Int) = viewModelScope.launch(Dispatchers.IO){
        val loveDateChange = loveDate.value
        if(loveDateChange != null){
            loveDateChange.statusNotification = key
            loveDateRepository.updateLoveDate(loveDateChange)
        }
    }
}


