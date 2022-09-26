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
import com.example.getirclone.home.tablayout.DetaylarFragment
import com.example.getirclone.home.tablayout.IcindekilerFragment
import kotlinx.coroutines.launch

class UrunViewModel(val urundb:UrunlerDao, application: Application):AndroidViewModel(application) {

    private val _urunTabLayoutHeaderList = MutableLiveData<List<String>>()
    val urunTablayoutHeaderList : LiveData<List<String>> get() = _urunTabLayoutHeaderList

    private val _urunTabLayoutList = MutableLiveData<List<Fragment>>()
    val urunTablayoutList : LiveData<List<Fragment>> get() = _urunTabLayoutList

    fun urunAdetPlusUpdate(urun: Urunler) {
        viewModelScope.launch {
            urun.urun_adet++
            val newUrun = Urunler(urun.urun_id,urun.urun_ad,urun.urun_fiyat,urun.urun_resim,urun.kategori_id,urun.urun_adet,urun.urun_detaylar,urun.urun_icindekiler)
            urundb.update(newUrun)
            Log.i("kola adet",newUrun.urun_adet.toString())
        }
    }

    fun urunTablayout(urun:Urunler){
        val fragmentList = arrayListOf<Fragment>()

        fragmentList.add(DetaylarFragment(urun.urun_detaylar))
        fragmentList.add(IcindekilerFragment(urun.urun_icindekiler))

        _urunTabLayoutList.value = fragmentList

        val fragmentHeaderList = arrayListOf<String>()
        fragmentHeaderList.add("Detaylar")
        fragmentHeaderList.add("İçindekiler")

        _urunTabLayoutHeaderList.value = fragmentHeaderList
    }
}