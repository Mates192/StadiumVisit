package com.stadiumvisit.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StadiumUserStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: StadiumUserState)

    @Query("SELECT * FROM stadium_user_states WHERE userId = :userId AND stadiumId = :stadiumId LIMIT 1")
    suspend fun get(userId: Long, stadiumId: String): StadiumUserState?
}
