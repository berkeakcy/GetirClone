package com.example.getirclone.home.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.getirclone.home.dao.UrunlerDao
import com.example.getirclone.home.dataclass.Urunler



@Database(entities = [Urunler::class], version = 3, exportSchema = true)
abstract class UrunlerDatabase : RoomDatabase() {

    abstract fun urunlerDao() : UrunlerDao

    companion object {

        @Volatile
        private var INSTANCE: UrunlerDatabase? = null

        fun getUrunlerDatabase(context: Context) : UrunlerDatabase? {
            kotlin.synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UrunlerDatabase::class.java,
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