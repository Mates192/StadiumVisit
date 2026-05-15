package com.stadiumvisit.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStatus(value: VisitStatus?): String? = value?.name

    @TypeConverter
    fun toStatus(value: String?): VisitStatus? = value?.let { VisitStatus.valueOf(it) }
}
