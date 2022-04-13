package com.talejalilov.kotlincountrylistmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.talejalilov.kotlincountrylistmvvm.R
import com.talejalilov.kotlincountrylistmvvm.databinding.ItemCountryBinding
import com.talejalilov.kotlincountrylistmvvm.model.Country
import com.talejalilov.kotlincountrylistmvvm.util.downloadFromUrl
import com.talejalilov.kotlincountrylistmvvm.util.placeHolderProgressBar
import talejalilov.kotlincountries.view.FeedFragmentDirections

class CountryAdapter (private val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {


    class CountryViewHolder(var view : ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_country, parent , false)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater, R.layout.item_country, parent, false)
        return CountryViewHolder(view)

    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.country = countryList[position]
        holder.view.listener = this

        /*
        holder.name.text = countryList[position].countryName
        holder.region.text = countryList[position].countryRegion

        holder.view.setOnClickListener{
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.imageView.downloadFromUrl(countryList[position].imageUrl,placeHolderProgressBar(holder.view.context))

*/
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList( newCountryList :List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()


    }

    override fun onCountryClicked(v: View) {
        val uuid = v.findViewById<TextView>(R.id.countryUuidText).text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        //action.countryUuid
        Navigation.findNavController(v).navigate(action)
//
//        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
//        Navigation.findNavController(it).navigate(action)
    }
}


