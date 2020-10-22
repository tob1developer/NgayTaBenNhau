package com.kietngo.ngaytabennhau.ui.fragment.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import com.kietngo.ngaytabennhau.repository.*
import com.kietngo.ngaytabennhau.repository.model.LoveDate
import com.kietngo.ngaytabennhau.repository.model.Quote
import com.kietngo.ngaytabennhau.repository.model.User
import com.kietngo.ngaytabennhau.repository.repository.ColorRepository
import com.kietngo.ngaytabennhau.repository.repository.LoveDateRepository
import com.kietngo.ngaytabennhau.repository.repository.QuoteRepository
import com.kietngo.ngaytabennhau.repository.repository.UserRepository
import com.kietngo.ngaytabennhau.ui.model.ButtonUi
import com.kietngo.ngaytabennhau.ui.model.ColorUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.random.Random

class HomeViewModel constructor(
    private val application: Application
): ViewModel() {

    private val userRepository : UserRepository
    private val colorRepository : ColorRepository
    private val loveDateRepository : LoveDateRepository
    private val quoteRepository: QuoteRepository

    //TODO: Color dialogFragment
    val listColor : LiveData<List<ColorUi>>

    val loveDate : LiveData<LoveDate>

    //TODO: chuyen toi man hinh quote
    private val _navigateQuoteFragment = MutableLiveData<Event<NavDirections>>()
    val navigateQuoteFragment : LiveData<Event<NavDirections>> = _navigateQuoteFragment

    private val _btnQuoteFragment = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                _navigateQuoteFragment.postValue(Event(HomeFragmentDirections.actionHomeFragmentToQuoteFragment()))
            }
        )
    }
    val btnQuoteFragment : LiveData<ButtonUi> = _btnQuoteFragment
//
//
//    // chuyen den man hinh date
//    private val _navigateDateFragment = MutableLiveData<Event<NavDirections>>()
//    val navigateDateFragment : LiveData<Event<NavDirections>> = _navigateDateFragment
//
//    private val _btnDateFragment = MutableLiveData<ButtonUi>().apply {
//        value = ButtonUi(
//            onClick = {
//                val action = HomeFragmentDirections.actionHomeFragmentToDateFragment()
//                _navigateDateFragment.postValue(Event(action))
//            }
//        )
//    }
//    val btnDateFragment : LiveData<ButtonUi> = _btnDateFragment
//
//
//    //chuyen den man hinh setting
//    private val _navigateSettingFragment = MutableLiveData<Event<NavDirections>>()
//    val navigateSettingFragment : LiveData<Event<NavDirections>> = _navigateSettingFragment
//
//    private val _btnSettingFragment = MutableLiveData<ButtonUi>().apply {
//        value = ButtonUi (
//            onClick ={
//                val action = HomeFragmentDirections.actionHomeFragmentToSettingFragment()
//                _navigateSettingFragment.postValue(Event(action))
//            }
//        )
//    }
//    val btnSettingFragment : LiveData<ButtonUi> = _btnSettingFragment
//
//
    //chuyen den man hinh Profile dialog
    private val _navigateProfileDialogFragment = MutableLiveData<Event<Int>>()
    val navigateProfileDialogFragment : LiveData<Event<Int>> = _navigateProfileDialogFragment


    private val _btnProfileDialogFragmentUser1
            = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                _navigateProfileDialogFragment.postValue(Event(ID_USER_1))
            }
        )
    }
    val btnProfileDialogFragmentUser1 : LiveData<ButtonUi> = _btnProfileDialogFragmentUser1

    private val _btnProfileDialogFragmentUser2
            = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                _navigateProfileDialogFragment.postValue(Event(ID_USER_2))
            }
        )
    }
    val btnProfileDialogFragmentUser2 : LiveData<ButtonUi> = _btnProfileDialogFragmentUser2


    private val _navigateColorDialogFragment = MutableLiveData<Event<NavDirections>>()
    val navigateColorDialogFragment : LiveData<Event<NavDirections>> = _navigateColorDialogFragment

    private val _btnGetColor
            = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                val action
                        = HomeFragmentDirections.actionHomeFragmentToColorDialogFragment()
                _navigateColorDialogFragment.postValue(Event(action))
            }
        )
    }
    val btnGetColor : LiveData<ButtonUi> = _btnGetColor

    val user1 : LiveData<User>


    val user2 : LiveData<User>

    //TODO: Load QuoteInHome Fragment
    private val _quoteInHomeFragment : MutableLiveData<Quote> by lazy {
        MutableLiveData<Quote>()
    }
    val quoteInHomeFragment : LiveData<Quote> = _quoteInHomeFragment




//    //Profile Dialog
//    val getUserInProfileDialog : MutableLiveData<User> by lazy {
//        MutableLiveData<User>()
//    }
//
//    private val _btnChangeAvatar = MutableLiveData<ButtonUi>().apply {
//        value = ButtonUi(
//            onClick = {
//                _navigateToGallery.postValue(Event(true))
//            }
//        )
//    }
//    val btnChangeAvatar: LiveData<ButtonUi> = _btnChangeAvatar
//
//    private val _navigateToGallery = MutableLiveData<Event<Boolean>>()
//    val navigateToGallery: LiveData<Event<Boolean>> = _navigateToGallery
//
//    private val _btnSaveProfile = MutableLiveData<ButtonUi>().apply {
//        value = ButtonUi(
//            onClick = {
//                _navigateSaveAndToBackHome.postValue(Event(true))
//            }
//        )
//    }
//    val btnSaveProfile : LiveData<ButtonUi> = _btnSaveProfile
//
//    private val _navigateSaveAndToBackHome = MutableLiveData<Event<Boolean>>()
//    val navigateSaveAndToBackHome : LiveData<Event<Boolean>> = _navigateSaveAndToBackHome
//
//    private val _btnSetNameUser = MutableLiveData<ButtonUi>().apply {
//        value = ButtonUi(
//            onClick = {
//                _changeNameUser.postValue(Event(true))
//            }
//        )
//    }
//    val btnSetNameUser : LiveData<ButtonUi> = _btnSetNameUser
//
//    private val _changeNameUser = MutableLiveData<Event<Boolean>>()
//    val changeNameUser : LiveData<Event<Boolean>> = _changeNameUser
//
//    private val _btnChangeDate = MutableLiveData<ButtonUi>().apply {
//        value = ButtonUi(
//            onClick = {
//                _navigateDatePicker.postValue(Event(true))
//            }
//        )
//    }
//    val btnChangeDate : LiveData<ButtonUi> = _btnChangeDate
//
//    private val _navigateDatePicker = MutableLiveData<Event<Boolean>>()
//    val navigateDatePicker : LiveData<Event<Boolean>> = _navigateDatePicker

    //date picker
//    val getCalendar : MutableLiveData<Calendar> by lazy {
//        MutableLiveData<Calendar>()
//    }
//
//    //Color dialog
//    private val _listColor : MutableLiveData<List<Color>> by lazy {
//        MutableLiveData<List<Color>>()
//    }
//    val listColor : LiveData<List<ColorUi>> = _listColor.map {
//        it.map { colorToList ->
//            ColorUi(
//                color = colorToList,
//                onClick = {
//                    setColorHeartView.postValue(colorToList.ColorToHex)
//                }
//            )
//        }
//    }
//
//    val setColorHeartView : MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }
//
//
//    //bo sung in HomeFragment
//    val topTitle : MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }
//
//    val bottomTitle : MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }
//
//    val dayTogether : MutableLiveData<String> by lazy {
//        MutableLiveData<String>()
//    }

    // TODO: khoi tao
    init {
        // get User
//        viewModelScope.launch(Dispatchers.IO){
//
//            val listColorToDatabase = database.colorDao().getAllColor()
//            val colorLove = database.loveDateDao().loadLoveDate(ID_LOVE_DATE)
//            if(colorLove != null){
//                val day = colorLove.startDate?.let { setDay(it) }
//                dayTogether.postValue(day)
//                topTitle.postValue(colorLove.topTitle)
//                bottomTitle.postValue(colorLove.bottomTitle)
//            }
//
//            withContext(Dispatchers.Main){
//
//                _listColor.postValue(listColorToDatabase)
//                setColorHeartView.postValue(colorLove.loveColor)
//            }
//        }

        val userDao = AppDatabase.getDatabase(application, viewModelScope).userDao()
        userRepository = UserRepository(userDao)
        user1 = userRepository.user1
        user2 = userRepository.user2

        val colorDao = AppDatabase.getDatabase(application, viewModelScope).colorDao()
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

        val loveDateDao = AppDatabase.getDatabase(application, viewModelScope).loveDateDao()
        loveDateRepository = LoveDateRepository(loveDateDao)
        loveDate = loveDateRepository.loveDate

        val quoteDao = AppDatabase.getDatabase(application,viewModelScope).quoteDao()
        quoteRepository = QuoteRepository(quoteDao)

    }

//    fun saveUserToDb(user: User){
//        viewModelScope.launch(Dispatchers.IO){
//            database.userDao().updateUser(user)
//        }
//        Timber.d("Update user to database")
//    }
//
    fun randomQuoteWithInt(number : Int){
        viewModelScope.launch(Dispatchers.IO){
            val randomQuoteToList = quoteRepository.loadQuoteWithId(number)
            withContext(Dispatchers.Main){
                _quoteInHomeFragment.postValue(randomQuoteToList)
            }
        }
    }

    fun saveColorLove(color : String){
        viewModelScope.launch (Dispatchers.IO){
            val saveLoveDate = loveDate.value

            if(saveLoveDate != null){
                saveLoveDate.loveColor = color
                loveDateRepository.updateLoveDate(saveLoveDate)
            }else
                Toast.makeText(
                    application,
                    "Gặp sự cố, không thể save.",
                    Toast.LENGTH_SHORT
                ).show()


        }
    }

//
//    private fun setDay(calendar: Calendar): String{
//        val today: Calendar = Calendar.getInstance()
//        val time = today.time.time - calendar.time.time
//
//        val day = time/(1000*60*60*24)
//        return "$day day"
//    }
}

