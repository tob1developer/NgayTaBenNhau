package com.kietngo.ngaytabennhau.ui.model

import com.kietngo.ngaytabennhau.repository.model.User

data class UserUi(
    val user: User,
    val onClick: () -> Unit
)