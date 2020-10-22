package com.kietngo.ngaytabennhau.ui.fragment.shareviewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class ShareHomeQuoteViewModel constructor(

): ViewModel(){

    val numberIdQuote = MutableLiveData<Int>().apply {
        value =  Random.nextInt(1,5)
    }
    val idQuote : LiveData<Int> = numberIdQuote

}