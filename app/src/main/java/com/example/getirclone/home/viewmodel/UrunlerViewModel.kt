package com.example.getirclone.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.getirclone.home.dao.UrunlerDao
import com.example.getirclone.home.dataclass.Urunler
import com.example.getirclone.home.tablayout.*
import kotlinx.coroutines.launch

class UrunlerViewModel(val urundb:UrunlerDao, application: Application):AndroidViewModel(application) {

    private val _urunlerList = MutableLiveData<List<Urunler>>()
    val urunlerList : LiveData<List<Urunler>> get() = _urunlerList

    private val _tabLayoutList = MutableLiveData<List<Fragment>>()
    val tabLayoutList : LiveData<List<Fragment>> get() = _tabLayoutList

    private val _tabLayoutHeaderList = MutableLiveData<List<String>>()
    val tabLayoutHeaderList : LiveData<List<String>> get() = _tabLayoutHeaderList

    private val _urunlerLoading = MutableLiveData<Boolean>(true)
    val urunlerLoading : LiveData<Boolean> get() = _urunlerLoading

    private val _urunAdet = MutableLiveData<Int>()
    val urunAdet : LiveData<Int> get() = _urunAdet

    init {
        _urunAdet.value = 0
    }

    fun getAllUrunler() {
        viewModelScope.launch {
            _urunlerList.value = urundb.allUrunler()
        }
    }

    fun urunAdd(urun: Urunler) {
        viewModelScope.launch {
            urundb.insert(urun)
        }
    }

    fun urunDelete(urun: Urunler) {
        viewModelScope.launch {
            urundb.delete(urun)
        }
    }

    fun urunUpdate(urun: Urunler) {
        viewModelScope.launch {
            urundb.update(urun)
        }
    }

    fun urunAdetPlusUpdate(urun: Urunler) {
        viewModelScope.launch {
            urun.urun_adet++
            val newUrun = Urunler(urun.urun_id,urun.urun_ad,urun.urun_fiyat,urun.urun_resim,urun.kategori_id,urun.urun_adet,urun.urun_detaylar,urun.urun_icindekiler)
            urundb.update(newUrun)
        }
    }

    fun urunFiltre(key: Int) {
        viewModelScope.launch {
            _urunlerLoading.value = false
            _urunlerList.value = urundb.urunfiltrele(key)
        }
    }

    fun urunSepette() {
        viewModelScope.launch {
            _urunlerList.value = urundb.urunSepette()
        }
    }


    fun tabLayout() {
        val fragmentList = arrayListOf<Fragment>()

        fragmentList.add(AtistirmalikFragment(1))
        fragmentList.add(AtistirmalikFragment(2))
        fragmentList.add(AtistirmalikFragment(3))
        fragmentList.add(AtistirmalikFragment(4))
        fragmentList.add(AtistirmalikFragment(5))
        fragmentList.add(AtistirmalikFragment(6))
        fragmentList.add(AtistirmalikFragment(7))
        fragmentList.add(AtistirmalikFragment(8))

        _tabLayoutList.value = fragmentList

        val fragmentHeaderList = arrayListOf<String>()
        fragmentHeaderList.add("Yeni Ürünler")
        fragmentHeaderList.add("İndirimler")
        fragmentHeaderList.add("Damacana")
        fragmentHeaderList.add("Su ve İçecek")
        fragmentHeaderList.add("Meyve")
        fragmentHeaderList.add("Fırından")
        fragmentHeaderList.add("Temel Gıda")
        fragmentHeaderList.add("Atıştırmalık")

        _tabLayoutHeaderList.value = fragmentHeaderList
    }
}