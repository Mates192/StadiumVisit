package com.stadiumvisit.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.stadiumvisit.data.seed.SeedStadiums

@Database(entities = [Stadium::class], version = 1, exportSchema = false)
abstract class StadiumDatabase : RoomDatabase() {

    abstract fun stadiumDao(): StadiumDao

    companion object {
        @Volatile private var instance: StadiumDatabase? = null

        fun getInstance(context: Context): StadiumDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    StadiumDatabase::class.java,
                    "stadium_visit.db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            val sql = SeedStadiums.insertStatements()
                            sql.forEach(db::execSQL)
                        }
                    }
                }).build().also { instance = it }
            }

        private fun ioThread(block: () -> Unit) {
            Thread(block).start()
        }
    }
}
