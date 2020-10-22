package com.kietngo.ngaytabennhau.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kietngo.ngaytabennhau.repository.model.Quote


@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote")
    fun getAllQuote(): LiveData<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertQuote(quote: Quote)

    @Delete
    suspend fun delete(quote: Quote)

    @Query("SELECT * FROM quote WHERE id = :quoteID")
    fun loadQuoteWithId(quoteID: Int): Quote

    @Update
    suspend fun updateQuote(vararg quote: Quote)
}