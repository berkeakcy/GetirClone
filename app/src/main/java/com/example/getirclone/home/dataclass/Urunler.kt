package com.example.getirclone.home.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "urunler")
data class Urunler(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "urunId") @NotNull
    var urun_id:Int = 0,
    @ColumnInfo(name = "urunAd") @NotNull
    var urun_ad:String,
    @ColumnInfo(name = "urunFiyat") @NotNull
    var urun_fiyat: String,
    @ColumnInfo(name = "urunResim") @NotNull
    var urun_resim:String,
    @ColumnInfo(name = "kategoriId") @NotNull
    var kategori_id: Int,
    @ColumnInfo(name = "urunAdet", defaultValue = "0") @NotNull
    var urun_adet:Int,
    @ColumnInfo(name = "urun_detaylar")@NotNull
    var urun_detaylar:String,
    @ColumnInfo(name = "urun_icindekiler")@NotNull
    var urun_icindekiler: String,
/*@ColumnInfo(name = "urunKullanim")
var urun_kullanim: String*/
):Serializable
