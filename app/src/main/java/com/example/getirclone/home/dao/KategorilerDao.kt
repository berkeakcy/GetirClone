package com.example.getirclone.home.dao

import androidx.room.*
import com.example.getirclone.home.dataclass.Kategoriler

@Dao
interface KategorilerDao {

    @Insert()
    suspend fun insert(kategoriler: Kategoriler)

    @Delete
    suspend fun delete(kategoriler: Kategoriler)

    @Query("SELECT * FROM kategoriler")
    suspend fun allKategori():List<Kategoriler>

    @Query("DELETE FROM kategoriler")
    suspend fun allDelete()

}