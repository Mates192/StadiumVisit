package com.stadiumvisit.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface StadiumDao {
    @Query("SELECT * FROM stadiums ORDER BY country, league, name")
    suspend fun getAllStadiums(): List<Stadium>

    @Query("""
        SELECT s.id, s.name, s.country, s.league, s.sizeTier, s.capacity, s.latitude, s.longitude, u.status as status
        FROM stadiums s
        LEFT JOIN stadium_user_states u ON u.stadiumId = s.id AND u.userId = :userId
        ORDER BY s.country, s.league, s.name
    """)
    suspend fun getAllForUser(userId: Long): List<StadiumWithUserState>
}
