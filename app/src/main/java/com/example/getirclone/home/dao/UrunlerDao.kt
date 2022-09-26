package com.example.getirclone.home.dao

import androidx.room.*
import com.example.getirclone.home.dataclass.Urunler

@Dao
interface UrunlerDao {
    @Insert
    suspend fun insert(urunler:Urunler)

    @Delete
    suspend fun delete(urunler:Urunler)
    @Update
    suspend fun update(urunler:Urunler)

    @Query("SELECT * FROM urunler")
    suspend fun allUrunler():List<Urunler>

    @Query("SELECT * FROM urunler WHERE kategoriId = :key")
    suspend fun urunfiltrele(key:Int):List<Urunler>

    @Query("SELECT * FROM urunler WHERE urunAdet>=1")
    suspend fun urunSepette():List<Urunler>

}