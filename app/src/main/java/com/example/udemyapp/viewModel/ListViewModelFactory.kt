package com.example.udemyapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ListViewModelFactory:ViewModelProvider.NewInstanceFactory() {
    private var application:Application?= null


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return application?.let { ListViewModel(it) } as T
    }

    fun setApplication(app: Application?){
        application=app
    }




}