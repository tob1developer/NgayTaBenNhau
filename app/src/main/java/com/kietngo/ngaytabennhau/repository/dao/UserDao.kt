package com.kietngo.ngaytabennhau.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kietngo.ngaytabennhau.repository.model.User
import java.util.concurrent.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(vararg user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user WHERE id = :userID")
    fun loadUserWithId(userID: Int) : LiveData<User>

    @Update
    suspend fun updateUser(user : User)

}