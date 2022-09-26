package com.example.getirclone.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getirclone.databinding.FragmentUrunlerBinding
import com.example.getirclone.home.databases.UrunlerDatabase
import com.example.getirclone.home.viewmodel.UrunlerViewModel
import com.example.getirclone.home.viewmodelfactory.UrunlerViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_urunler.*


class UrunlerFragment() : Fragment() {
    private lateinit var binding:FragmentUrunlerBinding
    private lateinit var fragmentList:List<Fragment>
    private lateinit var fragmentHeaderList : List<String>
    val args:UrunlerFragmentArgs by navArgs()
    private lateinit var urunlerViewModel: UrunlerViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUrunlerBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = UrunlerDatabase.getUrunlerDatabase(application)?.urunlerDao()

        val viewModelFactory = dataSource?.let { UrunlerViewModelFactory(it, application) }
        urunlerViewModel = viewModelFactory?.let {
            ViewModelProvider(this, it).get(UrunlerViewModel::class.java)
        }!!

        urunlerViewModel.tabLayout()
        urunlerViewModel.tabLayoutList.observe(viewLifecycleOwner){
            fragmentList = it
        }

        urunlerViewModel.tabLayoutHeaderList.observe(viewLifecycleOwner){
            fragmentHeaderList = it
        }
        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getList()
    }

    inner class MyViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
    fun getList(){
        urunlerViewModel.tabLayoutList.observe(viewLifecycleOwner){
            fragmentList = it
            val adapter = MyViewPagerAdapter(requireContext() as FragmentActivity)
            binding.viewpager2.adapter = adapter
        }

        urunlerViewModel.tabLayoutHeaderList.observe(viewLifecycleOwner){
            fragmentHeaderList = it
            TabLayoutMediator(tabLayout,binding.viewpager2){tab, position->
                tab.text = fragmentHeaderList[position]
                when (args.id){
                    1 -> binding.viewpager2.setCurrentItem(args.id-1,false)
                    2 -> binding.viewpager2.setCurrentItem(args.id-1,false)
                    3 -> binding.viewpager2.setCurrentItem(args.id-1,false)
                    4 -> binding.viewpager2.setCurrentItem(args.id-1,false)
                    5 -> binding.viewpager2.setCurrentItem(args.id-1,false)
                    6 -> binding.viewpager2.setCurrentItem(args.id-1,false)
                    7 -> binding.viewpager2.setCurrentItem(args.id-1,false)
                    8 -> binding.viewpager2.setCurrentItem(args.id-1,false)
                }
            }.attach()
        }
    }
}