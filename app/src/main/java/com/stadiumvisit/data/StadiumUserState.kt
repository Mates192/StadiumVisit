package com.stadiumvisit.data

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "stadium_user_states",
    primaryKeys = ["userId", "stadiumId"],
    indices = [Index(value = ["stadiumId"])]
)
data class StadiumUserState(
    val userId: Long,
    val stadiumId: String,
    val status: VisitStatus,
    val updatedAtEpochMillis: Long = System.currentTimeMillis()
)
