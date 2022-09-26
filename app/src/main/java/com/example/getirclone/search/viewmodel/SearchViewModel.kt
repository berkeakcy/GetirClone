package com.example.getirclone.search.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.getirclone.home.dao.UrunlerDao
import com.example.getirclone.home.dataclass.Urunler
import kotlinx.coroutines.launch

class SearchViewModel(val urundb:UrunlerDao,application: Application):AndroidViewModel(application) {

    private val _urunlerList = MutableLiveData<List<Urunler>>()
    val urunlerList : LiveData<List<Urunler>> get() = _urunlerList

    init {
        getAllUrunler()
    }

    fun getAllUrunler(){
        viewModelScope.launch {
            _urunlerList.value = urundb.allUrunler()
        }
    }
    fun urunAdetPlusUpdate(urun: Urunler) {
        viewModelScope.launch {
            urun.urun_adet++
            val newUrun = Urunler(urun.urun_id,urun.urun_ad,urun.urun_fiyat,urun.urun_resim,urun.kategori_id,urun.urun_adet,urun.urun_detaylar,urun.urun_icindekiler)
            urundb.update(newUrun)
        }
    }

}