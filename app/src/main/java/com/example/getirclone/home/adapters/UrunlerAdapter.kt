package com.example.getirclone.home.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.manager.Lifecycle
import com.example.getirclone.R
import com.example.getirclone.home.databases.UrunlerDatabase
import com.example.getirclone.home.dataclass.Urunler
import com.example.getirclone.home.fragments.UrunlerFragmentDirections
import com.example.getirclone.home.viewmodel.UrunlerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class UrunlerAdapter(private val mContext: Context, private val urunlerList:List<Urunler>,
                    private val urunlerViewModel: UrunlerViewModel
)
    :RecyclerView.Adapter<UrunlerAdapter.UrunlerCardTasarimTutucu>(){
        inner class UrunlerCardTasarimTutucu(tasarim: View):RecyclerView.ViewHolder(tasarim){
            var urun_card:CardView
            var urun_img:ImageView
            var urun_ad:TextView
            var urun_fiyat:TextView
            var urun_button:Button
            init {
                urun_card = tasarim.findViewById(R.id.urunler_cardView)
                urun_img = tasarim.findViewById(R.id.urunImage)
                urun_ad = tasarim.findViewById(R.id.urunAdText)
                urun_fiyat = tasarim.findViewById(R.id.fiyatText)
                urun_button = tasarim.findViewById(R.id.addButton)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrunlerCardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.urunler_card_tasarim,parent,false)
        return UrunlerCardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: UrunlerCardTasarimTutucu, position: Int) {

        val urun = urunlerList.get(position)
        holder.urun_ad.text = urun.urun_ad
        holder.urun_fiyat.text = urun.urun_fiyat
        holder.urun_img.setImageResource(mContext.resources.getIdentifier(urun.urun_resim,"drawable",mContext.packageName))
        holder.urun_card.setOnClickListener{
            it.findNavController().navigate(UrunlerFragmentDirections.actionUrunlerFragmentToUrunFragment(urun))
        }
        holder.urun_button.setOnClickListener(){
            if(urun.urun_adet < 5){
                urunlerViewModel.urunAdetPlusUpdate(urun)
                Toast.makeText(mContext,"Ürün Sepetinize Eklendi",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(mContext,"Aynı Üründen Daha Fazla Sepete Ekleyemezsiniz",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return urunlerList.size
    }


}