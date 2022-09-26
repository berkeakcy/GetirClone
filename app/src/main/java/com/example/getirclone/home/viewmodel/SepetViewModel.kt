package com.example.getirclone.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.getirclone.home.dao.UrunlerDao
import com.example.getirclone.home.dataclass.Urunler
import kotlinx.coroutines.launch

class SepetViewModel (val urundb:UrunlerDao,application: Application):AndroidViewModel(application){

    private val _urunlerList = MutableLiveData<List<Urunler>>()
    val urunlerList : LiveData<List<Urunler>> get() = _urunlerList

    private val _urunAdet = MutableLiveData<Int>()
    val urunAdet : LiveData<Int> get() = _urunAdet

    private val _tutar = MutableLiveData<Double>(0.0)
    val tutar : LiveData<Double> get() = _tutar


    fun urunSepette() {
        viewModelScope.launch {
            _urunlerList.value = urundb.urunSepette()
            _tutar.value = 0.0
            for (i in _urunlerList.value!!){
                _tutar.value = _tutar.value?.plus(i.urun_fiyat.toDouble()*i.urun_adet)
            }
        }
    }

    fun urunAdetPlusUpdate(urun: Urunler) {
        viewModelScope.launch {
            urun.urun_adet++
            val newUrun = Urunler(urun.urun_id,urun.urun_ad,urun.urun_fiyat,urun.urun_resim,urun.kategori_id,urun.urun_adet,urun.urun_detaylar,urun.urun_icindekiler)
            urundb.update(newUrun)
            _urunAdet.value = newUrun.urun_adet
            _tutar.value = _tutar.value?.plus(newUrun.urun_fiyat.toDouble())
        }
    }

    fun urunAdetMinusUpdate(urun:Urunler){
        viewModelScope.launch {
            urun.urun_adet--
            val newUrun = Urunler(urun.urun_id,urun.urun_ad,urun.urun_fiyat,urun.urun_resim,urun.kategori_id,urun.urun_adet,urun.urun_detaylar,urun.urun_icindekiler)
            urundb.update(newUrun)
            _urunAdet.value = newUrun.urun_adet
            _tutar.value = _tutar.value?.minus(newUrun.urun_fiyat.toDouble())
        }
    }
    fun urunDelete(urun: Urunler){
        viewModelScope.launch {
            urundb.delete(urun)
        }
    }
}