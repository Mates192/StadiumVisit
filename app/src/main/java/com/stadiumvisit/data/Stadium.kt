package com.stadiumvisit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stadiums")
data class Stadium(
    @PrimaryKey val id: String,
    val name: String,
    val country: String,
    val league: String,
    val sizeTier: String,
    val capacity: Int,
    val latitude: Double,
    val longitude: Double
)
