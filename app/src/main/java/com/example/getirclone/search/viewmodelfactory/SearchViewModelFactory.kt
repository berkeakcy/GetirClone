package com.example.getirclone.search.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.getirclone.home.dao.UrunlerDao
import com.example.getirclone.search.viewmodel.SearchViewModel

class SearchViewModelFactory(
    private val dataSource:UrunlerDao,
    private val application: Application
):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class'ı")
    }
}