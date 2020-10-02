package com.kietngo.ngaytabennhau.ui.fragment.setting

import android.util.EventLogTags
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.kietngo.ngaytabennhau.repository.Event
import com.kietngo.ngaytabennhau.ui.model.ButtonUi

class SettingViewModel constructor(

): ViewModel(){

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



}


