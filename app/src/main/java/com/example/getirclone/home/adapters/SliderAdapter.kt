package com.example.getirclone.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.getirclone.R

class SliderAdapter(var list:List<Int>, var ctx: Context): PagerAdapter() {
    lateinit var layoutInflater: LayoutInflater
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(ctx)
        var view = layoutInflater.inflate(R.layout.slider_item,container,false)
        val img = view.findViewById<ImageView>(R.id.imageSlider)
        img.setImageResource(list.get(position))
        container.addView(view,0)
        return view
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}