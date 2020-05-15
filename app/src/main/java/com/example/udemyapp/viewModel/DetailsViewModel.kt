package com.example.udemyapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.udemyapp.model.DogBreed

class DetailsViewModel:ViewModel() {
    var dog=MutableLiveData<DogBreed>()
    fun fetch(){
        var dogBreed=DogBreed("1","Dalmation","15","xyz","PET","MILD","XYZ")
        dog.value=dogBreed
    }
}