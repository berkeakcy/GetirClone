package com.example.getirclone.home.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.DataSource
import com.example.getirclone.home.dao.KategorilerDao
import com.example.getirclone.home.dao.UrunlerDao
import com.example.getirclone.home.viewmodel.UrunlerViewModel

class UrunlerViewModelFactory(
    private val dataSource: UrunlerDao,
    private val application: Application
):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UrunlerViewModel::class.java)) {
            return UrunlerViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class'ı")
    }
}