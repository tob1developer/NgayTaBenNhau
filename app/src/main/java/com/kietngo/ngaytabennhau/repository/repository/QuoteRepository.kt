package com.kietngo.ngaytabennhau.repository.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.kietngo.ngaytabennhau.repository.dao.QuoteDao
import com.kietngo.ngaytabennhau.repository.model.Quote

class QuoteRepository(private val quoteDao: QuoteDao) {
    val listQuote : LiveData<List<Quote>> = quoteDao.getAllQuote()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertQuote(quote: Quote){
        quoteDao.insertQuote(quote)
    }

    fun loadQuoteWithId(id: Int) =
        quoteDao.loadQuoteWithId(id)


    @WorkerThread
    suspend fun updateQuote(quote: Quote){
        quoteDao.insertQuote(quote)
    }
}