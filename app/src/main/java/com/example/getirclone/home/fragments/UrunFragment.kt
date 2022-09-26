package com.example.getirclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.getirclone.databinding.FragmentUrunBinding
import com.example.getirclone.home.databases.UrunlerDatabase
import com.example.getirclone.home.viewmodel.UrunViewModel
import com.example.getirclone.home.viewmodel.UrunlerViewModel
import com.example.getirclone.home.viewmodelfactory.UrunViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_urun.*


class UrunFragment : Fragment(){
    private lateinit var binding:FragmentUrunBinding
    private lateinit var fragmentList : List<Fragment>
    private lateinit var fragmentHeaderList : List<String>
    private val args:UrunFragmentArgs by navArgs()
    private lateinit var urundb:UrunlerDatabase
    private lateinit var urunViewModel: UrunViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        urundb = UrunlerDatabase.getUrunlerDatabase(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUrunBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = UrunlerDatabase.getUrunlerDatabase(application)?.urunlerDao()

        val viewModelFactory = dataSource?.let { UrunViewModelFactory(it, application) }
        urunViewModel = viewModelFactory?.let {
            ViewModelProvider(this, it).get(UrunViewModel::class.java)
        }!!

        urunViewModel.urunTablayout(args.urun)
        urunViewModel.urunTablayoutList.observe(viewLifecycleOwner){
            fragmentList = it
        }
        urunViewModel.urunTablayoutHeaderList.observe(viewLifecycleOwner){
            fragmentHeaderList = it
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            urunImage.setImageResource(requireContext().resources.getIdentifier(args.urun.urun_resim,"drawable",requireContext().packageName))
            urunAdText.text = args.urun.urun_ad
            fiyatText.text = args.urun.urun_fiyat
        }

        binding.button.setOnClickListener(){
            if(args.urun.urun_adet<5){
                urunViewModel.urunAdetPlusUpdate(args.urun)
                Toast.makeText(requireContext(),"Ürün Sepetinize Eklendi", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(requireContext(),"Aynı Üründen Daha Fazla Sepete Ekleyemezsiniz",Toast.LENGTH_LONG).show()
            }
        }

        getUrunTabLayout()

    }

    inner class MyViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }

    fun getUrunTabLayout(){
        urunViewModel.urunTablayoutList.observe(viewLifecycleOwner){
            fragmentList = it
            val adapter = MyViewPagerAdapter(requireContext() as FragmentActivity)
            binding.viewPager.adapter = adapter
        }
        urunViewModel.urunTablayoutHeaderList.observe(viewLifecycleOwner){
            fragmentHeaderList = it
            TabLayoutMediator(tabLayout2,viewPager){tab, position->
                tab.setText(fragmentHeaderList[position])
            }.attach()
        }
    }
}