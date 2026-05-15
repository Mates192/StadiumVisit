package com.stadiumvisit.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface StadiumDao {
    @Query("SELECT * FROM stadiums ORDER BY country, league, name")
    suspend fun getAllStadiums(): List<Stadium>
}
