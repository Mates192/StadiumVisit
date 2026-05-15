package com.stadiumvisit.data

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun setUserId(userId: Long) {
        prefs.edit().putLong("user_id", userId).apply()
    }

    fun getUserId(): Long? {
        val value = prefs.getLong("user_id", -1)
        return if (value > 0) value else null
    }

    fun logout() {
        prefs.edit().remove("user_id").apply()
    }
}
