package com.example.getirclone.home.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.getirclone.R
import com.example.getirclone.home.dataclass.Kategoriler
import com.example.getirclone.home.fragments.AnaSayfaFragmentDirections

class KategorilerAdapter(private val mContext: Context, private val kategoriList:List<Kategoriler>)
    : RecyclerView.Adapter<KategorilerAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(tasarim:View):RecyclerView.ViewHolder(tasarim){
        var kategori_card:CardView
        var kategori_img:ImageView
        var kategori_ad:TextView
        init {
            kategori_card = tasarim.findViewById(R.id.kategori_cardview)
            kategori_img = tasarim.findViewById(R.id.kategoriImg)
            kategori_ad = tasarim.findViewById(R.id.kategoriAd)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.card_tasarim,parent,false)
        return CardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val kategori = kategoriList.get(position)
        holder.kategori_ad.text = kategori.kategori_ad
        holder.kategori_img.setImageResource(mContext.resources.getIdentifier(kategori.kategori_resim,"drawable",mContext.packageName))
        holder.kategori_card.setOnClickListener{
            it.findNavController().navigate(AnaSayfaFragmentDirections.actionAnaSayfaFragmentToUrunlerFragment(kategori.kategori_id))
        }
    }

    override fun getItemCount(): Int {
        return kategoriList.size
    }

}


