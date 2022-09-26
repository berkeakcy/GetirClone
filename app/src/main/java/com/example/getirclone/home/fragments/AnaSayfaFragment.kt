package com.example.getirclone.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.getirclone.databinding.FragmentAnaSayfaBinding
import com.example.getirclone.home.dataclass.Kategoriler
import com.example.getirclone.home.adapters.KategorilerAdapter
import com.example.getirclone.home.adapters.SliderAdapter
import com.example.getirclone.home.databases.KategorilerDatabase
import com.example.getirclone.home.viewmodel.AnaSayfaViewModel
import com.example.getirclone.home.viewmodelfactory.AnaSayfaViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AnaSayfaFragment:Fragment() {
    private lateinit var binding:FragmentAnaSayfaBinding
    private lateinit var kategoriList:List<Kategoriler>
    private lateinit var adapter:KategorilerAdapter
    private lateinit var anaSayfaViewModel: AnaSayfaViewModel
    private lateinit var imgs : List<Int>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnaSayfaBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = KategorilerDatabase.getKategorilerDatabase(application)?.kategorilerDao()
        val viewModelFactory = dataSource?.let{ AnaSayfaViewModelFactory(it, application)}
         anaSayfaViewModel = viewModelFactory?.let {
            ViewModelProvider(this, it).get(AnaSayfaViewModel::class.java)
        }!!

        anaSayfaViewModel.getSliderImages()
        anaSayfaViewModel.imgs.observe(viewLifecycleOwner){
            imgs = it
        }

        anaSayfaViewModel.kategorilerList.observe(viewLifecycleOwner){
            kategoriList = it
            adapter = KategorilerAdapter(requireContext(),kategoriList)
            binding.adapter = adapter
        }

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSlider()
        getKategoriler()
    }

    fun getSlider(){
        anaSayfaViewModel.imgs.observe(viewLifecycleOwner){
            imgs = it
            if (imgs.isEmpty()){
                Snackbar.make(requireView(), "Slider bulunamadı", 1000).show()
            }
            else{
                var sliderAdapter = SliderAdapter(imgs,requireContext())
                binding.viewPager.adapter = sliderAdapter
            }
        }
    }

    fun getKategoriler(){
        anaSayfaViewModel.kategorilerList.observe(viewLifecycleOwner){
            kategoriList = it
            binding.apply {
                if (kategoriList.isEmpty()) {
                    Snackbar.make(requireView(), "Ürün bulunamadı", 1000).show()
                } else {
                    kategoriRv.layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
                    kategoriRv.setHasFixedSize(true)
                }
            }
        }
    }

}