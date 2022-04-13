package com.talejalilov.kotlincountrylistmvvm.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {

    companion object{

        private val PREFERENCES_TIME = "preferences_time"

        private var sharedPreferences:SharedPreferences? = null

        @Volatile private var instance : CustomSharedPreferences? = null

        private val lock = Any()
        //varmi yokmu kontrol edeceyimiz yerdi
        operator fun invoke(context: Context) : CustomSharedPreferences = instance?: synchronized(
            lock){
            instance?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

          fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences{

            sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }

      fun savedTime(time:Long){

        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME, time)
        }
    }


    fun getTime() = sharedPreferences?.getLong(PREFERENCES_TIME, 0)
}