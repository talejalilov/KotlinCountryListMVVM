package com.talejalilov.kotlincountrylistmvvm.service

import com.talejalilov.kotlincountrylistmvvm.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    //atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>

}