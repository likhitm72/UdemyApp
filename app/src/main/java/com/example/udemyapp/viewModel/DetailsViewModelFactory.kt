package com.example.udemyapp.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object DetailsViewModelFactory:ViewModelProvider.NewInstanceFactory() {
    private var application:Application?= null

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return application?.let { DetailsViewModel(it) } as T
    }

    fun setApplication(app: Application?){
       application =app
    }

}