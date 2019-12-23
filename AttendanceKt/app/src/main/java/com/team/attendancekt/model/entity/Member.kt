package com.team.attendancekt.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val age: Int,
    val phone: String,
    val email: String,
    val photo: String,
    val barcode: String
)