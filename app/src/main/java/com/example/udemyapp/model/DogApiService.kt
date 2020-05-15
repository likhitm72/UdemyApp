package com.example.udemyapp.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogApiService {
    val baseUrl="https://raw.githubusercontent.com"
    val api=Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create()) //converts the json response to the data class
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// convert dataclass to single
        .build()
        .create(DogApi::class.java)

    fun getDogs():Single<List<DogBreed>>{
        return api.getDogs()
    }


}