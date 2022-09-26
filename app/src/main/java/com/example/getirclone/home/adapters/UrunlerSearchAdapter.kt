package com.example.getirclone.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.getirclone.MainActivity
import com.example.getirclone.R
import com.example.getirclone.search.fragments.SearchFragmentDirections
import com.example.getirclone.home.databases.UrunlerDatabase
import com.example.getirclone.home.dataclass.Urunler
import com.example.getirclone.home.fragments.UrunlerFragmentDirections
import com.example.getirclone.search.viewmodel.SearchViewModel

class UrunlerSearchAdapter(private val mContext: Context, private var urunlerList:List<Urunler>
                        ,private val searchViewModel: SearchViewModel)
    :RecyclerView.Adapter<UrunlerSearchAdapter.UrunlerSearchCardTasarimTutucu>(){
    val urundb = UrunlerDatabase.getUrunlerDatabase(mContext)
    inner class UrunlerSearchCardTasarimTutucu(tasarim: View):RecyclerView.ViewHolder(tasarim){
        var urun_card: CardView
        var urun_img: ImageView
        var urun_ad: TextView
        var urun_fiyat: TextView
        var urun_button: Button
        init {
            urun_card = tasarim.findViewById(R.id.urunler_cardView)
            urun_img = tasarim.findViewById(R.id.urunImage)
            urun_ad = tasarim.findViewById(R.id.urunAdText)
            urun_fiyat = tasarim.findViewById(R.id.fiyatText)
            urun_button = tasarim.findViewById(R.id.addButton)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrunlerSearchCardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.urunler_card_tasarim,parent,false)
        return UrunlerSearchCardTasarimTutucu(tasarim)
    }

    fun filterList(filterlist: ArrayList<Urunler>) {
        urunlerList = filterlist
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UrunlerSearchCardTasarimTutucu, position: Int) {
        val urun = urunlerList.get(position)
        holder.urun_ad.text = urun.urun_ad
        holder.urun_fiyat.text = urun.urun_fiyat
        holder.urun_img.setImageResource(mContext.resources.getIdentifier(urun.urun_resim,"drawable",mContext.packageName))
        holder.urun_card.setOnClickListener{
            it.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToUrunFragment2(urun))
        }
        holder.urun_button.setOnClickListener(){
            Toast.makeText(mContext,"Ürün Sepetinize Eklendi", Toast.LENGTH_LONG).show()
            if(urun.urun_adet < 5){
                searchViewModel.urunAdetPlusUpdate(urun)
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