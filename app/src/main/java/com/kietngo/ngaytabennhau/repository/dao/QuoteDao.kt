package com.kietngo.ngaytabennhau.repository.dao

import androidx.room.*
import com.kietngo.ngaytabennhau.repository.model.Quote


@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote")
    fun getAllQuote(): List<Quote>

    @Insert
    suspend fun insertQuote(vararg quote: Quote)

    @Delete
    suspend fun delete(quote: Quote)

    @Query("SELECT * FROM quote WHERE id = :quoteID")
    suspend fun loadQuoteWithId(quoteID: Int): Quote

    @Update
    suspend fun updateQuote(vararg quote: Quote)
}