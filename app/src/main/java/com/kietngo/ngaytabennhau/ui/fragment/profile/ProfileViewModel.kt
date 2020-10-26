package com.kietngo.ngaytabennhau.ui.fragment.profile

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.kietngo.ngaytabennhau.repository.*
import com.kietngo.ngaytabennhau.repository.model.Color
import com.kietngo.ngaytabennhau.repository.model.User
import com.kietngo.ngaytabennhau.repository.repository.ColorRepository
import com.kietngo.ngaytabennhau.repository.repository.UserRepository
import com.kietngo.ngaytabennhau.ui.model.ButtonUi
import com.kietngo.ngaytabennhau.ui.model.ColorUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class ProfileViewModel constructor(
    private val application: Application
) : ViewModel(){
    private val userRepository : UserRepository

    var user1 : LiveData<User>
    var user2 : LiveData<User>

    private val colorRepository: ColorRepository
    val listColor : LiveData<List<ColorUi>>

    //TODO: btn Change Border color
    private val _btnChangeBorder
            = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi (
            onClick = {
                _navigateChangeBorder.postValue(Event(true))
            }
        )
    }
    val btnChangBorder : LiveData<ButtonUi> = _btnChangeBorder

    private val _navigateChangeBorder = MutableLiveData<Event<Boolean>>()
    val navigateChangeBorder : LiveData<Event<Boolean>> = _navigateChangeBorder


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

    //TODO: Get date picker to dialog date picker
    val getCalendar : MutableLiveData<Calendar> by lazy {
        MutableLiveData<Calendar>()
    }

    //TODO: Khoi tao
    init {
        val userDao = AppDatabase.getDatabase(application, viewModelScope).userDao()
        userRepository = UserRepository(userDao)

        user1 = userRepository.user1
        user2 = userRepository.user2

        val colorDao = AppDatabase.getDatabase(application,viewModelScope).colorDao()
        colorRepository = ColorRepository(colorDao)

        listColor = colorRepository.listColor.map {
            it.map { colorToList ->
                ColorUi(
                    color = colorToList,
                    onClick = {
                    }
                )
            }
        }

    }

    //TODO: Save profile to database
    fun saveProfileUser(user: User) = viewModelScope.launch(Dispatchers.IO ){
        userRepository.updateUser(user)
    }

    fun saveColorLove(color : String, id: Int){
        viewModelScope.launch (Dispatchers.IO){

            if (id == ID_USER_1 ) {
                val user = user1.value
                if (user != null){
                    user.borderColor = color
                    viewModelScope.launch(Dispatchers.IO){
                        Timber.d("check color $color , ${user.borderColor}")
                        userRepository.updateUser(user)
                    }
                }
                else
                    Toast.makeText(
                        application,
                        "Gặp sự cố, không thể save.",
                        Toast.LENGTH_SHORT
                    ).show()
            }
            if (id == ID_USER_2 ) {
                val user = user2.value
                if (user != null){
                    user.borderColor = color
                    viewModelScope.launch(Dispatchers.IO){
                        userRepository.updateUser(user)
                    }
                }
                else
                    Toast.makeText(
                        application,
                        "Gặp sự cố, không thể save.",
                        Toast.LENGTH_SHORT
                    ).show()
            }

        }
    }


}