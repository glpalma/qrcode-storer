package com.example.qrcode_storer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Code(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo
    val data: String?
)
