package com.stadiumvisit.data

data class StadiumWithUserState(
    val id: String,
    val name: String,
    val country: String,
    val league: String,
    val sizeTier: String,
    val capacity: Int,
    val latitude: Double,
    val longitude: Double,
    val status: VisitStatus?
)
