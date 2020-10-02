package com.kietngo.ngaytabennhau.repository.dao

import androidx.room.*
import com.kietngo.ngaytabennhau.repository.model.User
import java.util.concurrent.Flow


@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(vararg user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user WHERE id = :userID")
    suspend fun loadUserWithId(userID: Int) : User

    @Update
    suspend fun updateUser(user : User)

}