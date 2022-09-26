package com.example.getirclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getirclone.databinding.FragmentSepetBinding
import com.example.getirclone.home.adapters.UrunlerSepetAdapter
import com.example.getirclone.home.databases.UrunlerDatabase
import com.example.getirclone.home.dataclass.Urunler
import com.example.getirclone.home.viewmodel.SepetViewModel
import com.example.getirclone.home.viewmodel.UrunlerViewModel
import com.example.getirclone.home.viewmodelfactory.SepetViewModelFactory
import com.example.getirclone.home.viewmodelfactory.UrunlerViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SepetFragment: Fragment() {
    private lateinit var binding:FragmentSepetBinding
    private lateinit var urunSepetList:List<Urunler>
    private lateinit var adapter:UrunlerSepetAdapter
    private lateinit var sepetViewModel: SepetViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSepetBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = UrunlerDatabase.getUrunlerDatabase(application)?.urunlerDao()

        val viewModelFactory = dataSource?.let { SepetViewModelFactory(it, application) }
        sepetViewModel = viewModelFactory?.let {
            ViewModelProvider(this, it).get(SepetViewModel::class.java)
        }!!

        sepetViewModel.urunSepette()
        sepetViewModel.urunlerList.observe(viewLifecycleOwner){
            urunSepetList = it as ArrayList<Urunler>
            adapter = UrunlerSepetAdapter(requireContext(),
                urunSepetList as ArrayList<Urunler>,sepetViewModel)
            binding.adapter = adapter
        }
        sepetViewModel.tutar.observe(viewLifecycleOwner){
            binding.fiyatButton.text = it.toString()
        }
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUrunler()
    }
    fun getUrunler(){
        sepetViewModel.urunlerList.observe(viewLifecycleOwner){
            urunSepetList = it
            binding.apply {
                if (urunSepetList.isEmpty()) {
                    Snackbar.make(requireView(), "Ürün bulunamadı", 1000).show()
                } else {
                    urunSepetRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                    adapter = UrunlerSepetAdapter(requireContext(),urunSepetList as ArrayList<Urunler>,sepetViewModel)
                    binding.adapter = adapter
                    urunSepetRv.setHasFixedSize(true)
                }
            }
        }
    }
}