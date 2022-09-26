package com.example.getirclone.home.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.getirclone.home.dao.KategorilerDao
import com.example.getirclone.home.dataclass.Kategoriler
import java.io.File

@Database(entities = [Kategoriler::class], version = 3, exportSchema = true)
abstract class KategorilerDatabase : RoomDatabase() {

    abstract fun kategorilerDao() : KategorilerDao

    companion object {

        @Volatile
        private var INSTANCE: KategorilerDatabase? = null

        fun getKategorilerDatabase(context: Context) : KategorilerDatabase? {
            kotlin.synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KategorilerDatabase::class.java,
                        "getirdatabase.sqlite"
                    )
                        .createFromAsset("getirdatabase.sqlite")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return INSTANCE
            }
        }
    }
}