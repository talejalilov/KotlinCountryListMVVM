package com.talejalilov.kotlincountrylistmvvm.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talejalilov.kotlincountrylistmvvm.model.Country
import com.talejalilov.kotlincountrylistmvvm.service.CountryAPIService
import com.talejalilov.kotlincountrylistmvvm.service.CountryDatabase
import com.talejalilov.kotlincountrylistmvvm.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime  = 10*60*1000*1000*1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshFromAPI(){
        getDataFromApi()
    }
    fun refreshData() {
        val updateTime  = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime ){

            getDataFromSQLite()
        }else {

            getDataFromApi()

        }

    }

    private fun getDataFromSQLite(){
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "From Sqlite", Toast.LENGTH_LONG).show()


        }
    }

    private fun getDataFromApi(){
        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {

                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "From Api", Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {

                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }



    private fun showCountries(countryList:List<Country>){
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>){

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong =  dao.insertAll(*list.toTypedArray()) // -> * list yəni arraydə tək tək insert elə

            var i = 0
            while (i<list.size){
                list[i].uuid  = listLong[i].toInt()
                i = i +1
            }

            showCountries(list)
        }

        customPreferences.savedTime(System.nanoTime())
    }


    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }


}