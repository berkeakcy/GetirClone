package com.example.getirclone.home.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "kategoriler")
data class Kategoriler(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "kategoriId") @NotNull
    var kategori_id:Int = 0,
    @ColumnInfo(name = "kategoriAd") @NotNull
    var kategori_ad:String,
    @ColumnInfo(name = "kategoriResim") @NotNull
    var kategori_resim:String):Serializable