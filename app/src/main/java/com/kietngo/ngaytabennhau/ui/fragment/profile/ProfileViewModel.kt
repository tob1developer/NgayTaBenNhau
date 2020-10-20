package com.kietngo.ngaytabennhau.ui.fragment.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kietngo.ngaytabennhau.repository.*
import com.kietngo.ngaytabennhau.repository.model.User
import com.kietngo.ngaytabennhau.ui.model.ButtonUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    //TODO: Save Profile
    private val _btnSaveProfile = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi (
            onClick =  {
                _navigateSaveAndToBackHome.postValue(Event(true))
            }
        )
    }
    val btnSaveProfile : LiveData<ButtonUi> = _btnSaveProfile

    private val _navigateSaveAndToBackHome = MutableLiveData<Event<Boolean>>()
    val navigateSaveAndToBackHome : LiveData<Event<Boolean>> = _navigateSaveAndToBackHome

    //TODO: change Name User
    private val _btnSetNameUser = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick =  {
                _changeNameUser.postValue(Event(true))
            }
        )
    }
    val btnSetNameUser : LiveData<ButtonUi> = _btnSetNameUser

    private val _changeNameUser = MutableLiveData<Event<Boolean>>()
    val changeNameUser : LiveData<Event<Boolean>> = _changeNameUser

    //TODO: set Date
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


    //TODO: Khoi tao
    init {
        val userDao = AppDatabase.getDatabase(application, viewModelScope).userDao()
        userRepository = UserRepository(userDao)

        user1 = userRepository.user1
        user2 = userRepository.user2
    }

    //TODO: Save profile to database
    fun saveProfileUser(user: User) = viewModelScope.launch(Dispatchers.IO ){
        userRepository.updateUser(user)
    }

}