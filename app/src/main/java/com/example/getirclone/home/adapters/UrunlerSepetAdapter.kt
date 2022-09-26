package com.example.getirclone.home.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.getirclone.MainActivity
import com.example.getirclone.R
import com.example.getirclone.SepetFragmentDirections
import com.example.getirclone.home.databases.UrunlerDatabase
import com.example.getirclone.home.dataclass.Urunler
import com.example.getirclone.home.viewmodel.SepetViewModel
import com.example.getirclone.home.viewmodel.UrunViewModel
import com.example.getirclone.home.viewmodel.UrunlerViewModel

class UrunlerSepetAdapter(private val mContext:Context,private val urunlerSepetList:ArrayList<Urunler>,
                        private val sepetViewModel: SepetViewModel
)
    :RecyclerView.Adapter<UrunlerSepetAdapter.UrunlerSepetTasarimTutucu>(){
    private val urundb = UrunlerDatabase.getUrunlerDatabase(mContext)
        inner class UrunlerSepetTasarimTutucu(tasarim: View):RecyclerView.ViewHolder(tasarim){
            var urun_card:CardView
            var urun_img:ImageView
            var urun_ad:TextView
            var urun_fiyat:TextView
            var azalt_button:Button
            var arttir_button:Button
            var adet_button:Button
            init {
                urun_card = tasarim.findViewById(R.id.urunler_sepet_cardView)
                urun_img = tasarim.findViewById(R.id.urunSepetImage)
                urun_ad = tasarim.findViewById(R.id.urunAdSepetText)
                urun_fiyat = tasarim.findViewById(R.id.urunFiyatSepetText)
                azalt_button = tasarim.findViewById(R.id.azaltButton)
                arttir_button = tasarim.findViewById(R.id.arttirButton)
                adet_button = tasarim.findViewById(R.id.adetButton)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrunlerSepetTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.urunler_sepet_tasarim,parent,false)
        return UrunlerSepetTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: UrunlerSepetAdapter.UrunlerSepetTasarimTutucu, position: Int) {
        var urun = urunlerSepetList[position]
        holder.urun_ad.text = urun.urun_ad
        holder.urun_fiyat.text = urun.urun_fiyat
        holder.urun_img.setImageResource(mContext.resources.getIdentifier(urun.urun_resim,"drawable",mContext.packageName))
        holder.urun_card.setOnClickListener(){
            it.findNavController().navigate(SepetFragmentDirections.actionSepetFragmentToUrunFragment2(urun))
        }
        holder.adet_button.text = urun.urun_adet.toString()
        holder.arttir_button.setOnClickListener(){
            if(urun.urun_adet in 0..4) {
                sepetViewModel.urunAdetPlusUpdate(urun)
                holder.adet_button.text = urun.urun_adet.toString()
            }
            else{
                Toast.makeText(mContext,"Aynı üründen daha fazla alamazsınız",Toast.LENGTH_SHORT).show()
            }
        }
        holder.azalt_button.setOnClickListener(){
            if(urun.urun_adet>0){
                sepetViewModel.urunAdetMinusUpdate(urun)
                holder.adet_button.text = urun.urun_adet.toString()
            }
            else{
                sepetViewModel.urunSepette()
                Toast.makeText(mContext,"Ürün sepet listenizden çıkartıldı",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return urunlerSepetList.size
    }

    fun urunSepetListUpdate(newUrunlerSepetList:List<Urunler>){
        urunlerSepetList.clear()
        urunlerSepetList.addAll(newUrunlerSepetList)
        notifyDataSetChanged()
    }

}