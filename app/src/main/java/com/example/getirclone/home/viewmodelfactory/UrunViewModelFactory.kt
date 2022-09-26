package com.example.getirclone.home.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.getirclone.home.dao.UrunlerDao
import com.example.getirclone.home.viewmodel.UrunViewModel
import com.example.getirclone.home.viewmodel.UrunlerViewModel

class UrunViewModelFactory (
    private val dataSource: UrunlerDao,
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UrunViewModel::class.java)) {
            return UrunViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class'ı")
    }
}