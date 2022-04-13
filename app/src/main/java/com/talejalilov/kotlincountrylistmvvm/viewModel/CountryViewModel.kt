package com.talejalilov.kotlincountrylistmvvm.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talejalilov.kotlincountrylistmvvm.model.Country
import com.talejalilov.kotlincountrylistmvvm.service.CountryDatabase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*

class CountryViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int){

        launch {

            val dao = CountryDatabase(getApplication()).countryDao()
            val country  =  dao.getCountry(uuid)
            countryLiveData.value = country
        }



    }
}