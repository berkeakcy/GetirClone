package com.example.getirclone

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.getirclone.databinding.ActivityMainBinding
import com.example.getirclone.home.databases.KategorilerDatabase
import com.example.getirclone.home.databases.UrunlerDatabase
import com.example.getirclone.home.dataclass.Urunler
import com.example.getirclone.home.viewmodel.SepetViewModel
import com.example.getirclone.home.viewmodel.UrunlerViewModel
import com.example.getirclone.home.viewmodelfactory.SepetViewModelFactory
import com.example.getirclone.home.viewmodelfactory.UrunlerViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()  {
    private lateinit var binding:ActivityMainBinding
    private lateinit var urunlerViewModel: UrunlerViewModel
    private lateinit var sepetViewModel: SepetViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

/*        val application = requireNotNull(this).application
        val dataSource = UrunlerDatabase.getUrunlerDatabase(application)?.urunlerDao()

        val viewModelFactory = dataSource?.let { SepetViewModelFactory(it, application) }
        sepetViewModel = viewModelFactory?.let {
            ViewModelProvider(this, it).get(SepetViewModel::class.java)
        }!!

        binding.lifecycleOwner = this*/

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottom_nav,navHostFragment.navController)

        binding.sepetButton.setOnClickListener(){
            navHostFragment.navController.navigateUp()
            navHostFragment.navController.navigate(R.id.sepetFragment)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }
}