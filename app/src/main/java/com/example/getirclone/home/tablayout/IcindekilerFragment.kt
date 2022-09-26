package com.example.getirclone.home.tablayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.getirclone.R
import com.example.getirclone.databinding.FragmentIcindekilerBinding


class IcindekilerFragment(private val urunIcindekiler:String) : Fragment() {
    private lateinit var binding: FragmentIcindekilerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIcindekilerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.icindekilerText.text = urunIcindekiler
    }


}