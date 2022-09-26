package com.example.getirclone.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.getirclone.R
import com.example.getirclone.home.dao.KategorilerDao
import com.example.getirclone.home.dataclass.Kategoriler
import kotlinx.coroutines.launch

class AnaSayfaViewModel(val kategoridb:KategorilerDao, application: Application):AndroidViewModel(application) {
    private val _kategorilerList = MutableLiveData<List<Kategoriler>>()
    val kategorilerList : LiveData<List<Kategoriler>> get() = _kategorilerList

    private val _imgs = MutableLiveData<List<Int>>()
    val imgs : LiveData<List<Int>> get() = _imgs

    init {
        getAllKategoriler()
    }

    private fun getAllKategoriler(){
        viewModelScope.launch {
            _kategorilerList.value = kategoridb.allKategori()
        }
    }
    fun addKategori(kategori:Kategoriler){
        viewModelScope.launch {
            kategoridb.insert(kategori)
        }
    }
    fun getSliderImages(){
        val images = arrayListOf<Int>()
        images.add(R.drawable.getirimgy)
        images.add(R.drawable.getirimg2y)
        images.add(R.drawable.getirimg3y)
        _imgs.value = images
    }
}