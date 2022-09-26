package com.example.getirclone.home.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.getirclone.home.dao.UrunlerDao
import com.example.getirclone.home.viewmodel.SepetViewModel
import com.example.getirclone.home.viewmodel.UrunViewModel

class SepetViewModelFactory(
    private val dataSource:UrunlerDao,
    private val application: Application
):ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SepetViewModel::class.java)) {
            return SepetViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class'ı")
    }
}