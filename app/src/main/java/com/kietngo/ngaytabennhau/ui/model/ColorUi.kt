package com.kietngo.ngaytabennhau.ui.model

import com.kietngo.ngaytabennhau.repository.model.Color

data class ColorUi(
    val color: Color,
    val onClick : () -> Unit  //set Color
)