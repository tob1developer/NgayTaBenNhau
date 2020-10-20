package com.kietngo.ngaytabennhau.ui.fragment.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kietngo.ngaytabennhau.repository.*
import com.kietngo.ngaytabennhau.repository.model.User
import com.kietngo.ngaytabennhau.ui.model.ButtonUi

class ProfileViewModel constructor(
    private val application: Application
) : ViewModel(){
    private val userRepository : UserRepository

    var user1 : LiveData<User>
    var user2 : LiveData<User>

    // TODO: Btn change Avatar
    private val _navigateToGallery = MutableLiveData<Event<Boolean>>()
    val navigateToGallery: LiveData<Event<Boolean>> = _navigateToGallery

    private val _btnChangeAvatar = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi (
            onClick = {
                _navigateToGallery.postValue(Event(true))
            }
        )
    }
    val btnChangeAvatar : LiveData<ButtonUi> = _btnChangeAvatar

    init {
        val userDao = AppDatabase.getDatabase(application, viewModelScope).userDao()
        userRepository = UserRepository(userDao)

        user1 = userRepository.user1
        user2 = userRepository.user2
    }
}