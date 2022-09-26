package com.example.getirclone.home.tablayout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.getirclone.databinding.FragmentAtistirmalikBinding
import com.example.getirclone.home.adapters.UrunlerAdapter
import com.example.getirclone.home.databases.UrunlerDatabase
import com.example.getirclone.home.dataclass.Urunler
import com.example.getirclone.home.viewmodel.UrunlerViewModel
import com.example.getirclone.home.viewmodelfactory.UrunlerViewModelFactory
import com.google.android.material.snackbar.Snackbar


class AtistirmalikFragment (val key:Int):Fragment() {
    private lateinit var binding:FragmentAtistirmalikBinding
    private lateinit var urundb:UrunlerDatabase
    private lateinit var urunList:List<Urunler>
    private lateinit var urunlerViewModel: UrunlerViewModel
    private lateinit var adapter: UrunlerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAtistirmalikBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = UrunlerDatabase.getUrunlerDatabase(application)?.urunlerDao()

        val viewModelFactory = dataSource?.let { UrunlerViewModelFactory(it, application) }
        urunlerViewModel = viewModelFactory?.let {
            ViewModelProvider(this, it).get(UrunlerViewModel::class.java)
        }!!

        urunlerViewModel.urunFiltre(key)
        urunlerViewModel.urunlerLoading.observe(viewLifecycleOwner){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }
            else{
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
        urunlerViewModel.urunlerList.observe(viewLifecycleOwner){
            urunList = it
            adapter = UrunlerAdapter(requireContext(),urunList,urunlerViewModel)
            binding.adapter = adapter
        }
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUrunler()
    }
    fun getUrunler(){
        urunlerViewModel.urunlerList.observe(viewLifecycleOwner){
            urunList = it
            binding.apply {
                if (urunList.isEmpty()) {
                    Snackbar.make(requireView(), "Ürün bulunamadı", 1000).show()
                } else {
                    urunlerRv.layoutManager = GridLayoutManager(context,3 ,GridLayoutManager.VERTICAL,false)
                    urunlerRv.setHasFixedSize(true)
                }
            }
        }
    }
}