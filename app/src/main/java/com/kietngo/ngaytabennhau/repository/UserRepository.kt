package com.kietngo.ngaytabennhau.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.kietngo.ngaytabennhau.repository.dao.UserDao
import com.kietngo.ngaytabennhau.repository.model.User

class UserRepository(private val userDao: UserDao) {

    val user1 : LiveData<User> = userDao.loadUserWithId(ID_USER_1)
    val user2 : LiveData<User> = userDao.loadUserWithId(ID_USER_2)


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUser(user : User){
        userDao.insertUser(user)
    }

    @WorkerThread
    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }
}