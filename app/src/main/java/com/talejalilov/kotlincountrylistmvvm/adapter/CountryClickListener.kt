package com.talejalilov.kotlincountrylistmvvm.adapter


import android.view.View
import com.talejalilov.kotlincountrylistmvvm.databinding.ItemCountryBinding

interface CountryClickListener {

    fun onCountryClicked(v : View)
}