package com.example.getirclone.search.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.getirclone.databinding.FragmentSearchBinding
import com.example.getirclone.home.adapters.UrunlerSearchAdapter
import com.example.getirclone.home.databases.UrunlerDatabase
import com.example.getirclone.home.dataclass.Urunler
import com.example.getirclone.search.viewmodel.SearchViewModel
import com.example.getirclone.search.viewmodelfactory.SearchViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_ana_sayfa.*
import java.util.*


class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var urundb:UrunlerDatabase
    private lateinit var urunlerList:List<Urunler>
    private lateinit var adapter:UrunlerSearchAdapter
    private lateinit var searchViewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = UrunlerDatabase.getUrunlerDatabase(application)?.urunlerDao()

        val viewModelFactory = dataSource?.let{ SearchViewModelFactory(it, application) }
        searchViewModel = viewModelFactory?.let {
            ViewModelProvider(this, it).get(SearchViewModel::class.java)
        }!!

        searchViewModel.urunlerList.observe(viewLifecycleOwner){
            urunlerList = it
            adapter = UrunlerSearchAdapter(requireContext(),urunlerList,searchViewModel)
            binding.araRv.adapter = adapter
        }
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUrunler()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filter(it) }
                return false
            }
        })
    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<Urunler> = ArrayList()
        searchViewModel.urunlerList.observe(viewLifecycleOwner){
            for (item in urunlerList) {
                if (item.urun_ad.toLowerCase().contains(text.toLowerCase())) {
                    filteredlist.add(item)
                }
            }
            if (filteredlist.isEmpty()) {
                Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
            } else {
                adapter.filterList(filteredlist)
            }
        }
    }

    fun getUrunler(){
        searchViewModel.urunlerList.observe(viewLifecycleOwner){
            urunlerList = it
            binding.apply {
                if (urunlerList.isEmpty()) {
                    Snackbar.make(requireView(), "Ürün bulunamadı", 1000).show()
                } else {
                    araRv.layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
                    binding.araRv.adapter = adapter
                    araRv.setHasFixedSize(true)
                }
            }
        }
    }
}