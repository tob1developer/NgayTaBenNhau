package com.kietngo.ngaytabennhau.ui.fragment.quote

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.kietngo.ngaytabennhau.repository.AppDatabase
import com.kietngo.ngaytabennhau.repository.Event
import com.kietngo.ngaytabennhau.repository.NAME_DATABASE
import com.kietngo.ngaytabennhau.repository.model.Quote
import com.kietngo.ngaytabennhau.ui.model.ButtonUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.random.Random

class QuoteViewModel constructor(
    private val applicationContext: Context
): ViewModel(){

    private var database : AppDatabase =
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, NAME_DATABASE)
            .build()


    private val _randomQuote: MutableLiveData<Quote> by lazy {
        MutableLiveData<Quote>()
    }
    val randomQuote : LiveData<Quote> = _randomQuote

    private val _btnRandomQuote = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {
                val number = Random.nextInt(1,5)
                idInShareViewModel.postValue(number)
                randomQuoteWithInt(number)
            }
        )
    }
    val btnRandomQuote : LiveData<ButtonUi> = _btnRandomQuote

    private val _btnShareQuote = MutableLiveData<ButtonUi>().apply {
        value = ButtonUi(
            onClick = {

                val content = _randomQuote.value?.content
                if (content != null)
                    _contentQuote.postValue(Event(content))
            }
        )
    }
    val btnShareQuote : LiveData<ButtonUi> = _btnShareQuote

    private val _contentQuote : MutableLiveData<Event<String>> by lazy {
        MutableLiveData<Event<String>>()
    }
    val contentQuote : LiveData<Event<String>> = _contentQuote

    val idInShareViewModel : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun randomQuoteWithInt(number : Int){
        viewModelScope.launch(Dispatchers.IO){
            val randomQuoteToList = database.quoteDao().loadQuoteWithId(number)
            withContext(Dispatchers.Main){
                _randomQuote.postValue(randomQuoteToList)
            }
        }
        Timber.d("random quote")
    }
}