package com.example.udemyapp.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.udemyapp.model.DogApiService
import com.example.udemyapp.model.DogBreed
import com.example.udemyapp.model.DogDatabase
import com.example.udemyapp.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application):BaseViewModel(application) {


    private var preferenceHelper=SharedPreferenceHelper()
    var dogs=MutableLiveData<List<DogBreed>>()
    var dogsLoadError=MutableLiveData<Boolean>()
    var loading= MutableLiveData<Boolean>()
    val dogApiService=DogApiService()
    val disposable=CompositeDisposable()
    private var refreshTime=10*1000*1000*1000L


    fun refresh(){
        Toast.makeText(getApplication(),"Refreshing",Toast.LENGTH_SHORT)
        val updatedTime=preferenceHelper.getUpdatedTime()
        if (updatedTime!=null && updatedTime!=0L && System.nanoTime()-updatedTime<refreshTime){
            fetchFromDatabase()
        }
        else{
            fetchFromRemote()
        }

    }

    private fun fetchFromDatabase(){
        loading.value=true
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dogsRetrieved(dao.getAllDogs())
            Toast.makeText(getApplication(),"Data retrieved from Database",Toast.LENGTH_LONG)
        }


    }

    private fun fetchFromRemote() {
        loading.value=true
        disposable.add(
            dogApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<DogBreed>>() {

                    override fun onSuccess(dogList: List<DogBreed>) {
                        storeDogsLocally(dogList)
                        Toast.makeText(getApplication(),preferenceHelper.getUpdatedTime().toString(),Toast.LENGTH_LONG)
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }


    private fun dogsRetrieved(dogList:List<DogBreed>){
        dogsLoadError.value=false
        dogs.value=dogList
        loading.value=false

    }


    private fun storeDogsLocally(dogList: List<DogBreed>){

        launch {
            val dao=DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            var result=dao.insertAll(*dogList.toTypedArray())
            var i=0
            while(i<dogList.size){
                dogList[i].uuid=result[i].toInt()
                i++
            }
            dogsRetrieved(dogList)
        }
        preferenceHelper.saveUpdateTime(System.nanoTime())

    }



    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}