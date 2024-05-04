package com.devmasterstudy.fitnesstracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Mart(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("food") val food: String,
    @ColumnInfo("amount") val amount: String,
    @ColumnInfo("unit") val unit: String,
    @ColumnInfo("created_date") val createdDate: Date = Date()
)
