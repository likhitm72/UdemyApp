package com.example.udemyapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.udemyapp.model.DogBreed
import com.example.udemyapp.model.DogDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application):BaseViewModel(application) {
    var dog=MutableLiveData<DogBreed>()
    fun fetch(dogId:Int){
        launch {
            var dao=DogDatabase(getApplication()).dogDao()
            dog.value=dao.getDog(dogId)
        }
    }
}