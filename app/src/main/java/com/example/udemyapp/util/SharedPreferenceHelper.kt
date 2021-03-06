package com.example.udemyapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferenceHelper {

    companion object{
        private var PREF_TIME="Pref_Time"
        private var prefs:SharedPreferences?=null
        @Volatile private var instance:SharedPreferenceHelper?=null
        private var LOCK=Any()

        operator fun invoke(context: Context):SharedPreferenceHelper= instance?: synchronized(LOCK){
            instance?:buildHelper(context).also{
                instance=it
            }
        }

        private fun buildHelper(context: Context):SharedPreferenceHelper{
            prefs=PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferenceHelper()
        }

    }


    fun saveUpdateTime(time: Long) {
        prefs?.edit(commit = true) {putLong(PREF_TIME, time)}
    }


    fun getUpdatedTime()= prefs?.getLong(PREF_TIME,0)
}