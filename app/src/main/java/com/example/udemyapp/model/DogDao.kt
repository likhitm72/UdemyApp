package com.example.udemyapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {

    //takes in dogs and returns uuid of the dogs inserted. suspended because we need to run it on separate thread.If not suspend we will get compilation error
    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>


    @Query("select * from dogbreed")
    suspend fun getAllDogs():List<DogBreed>

    @Query("select * from dogbreed where uuid=:dogId")
    suspend fun getDog(dogId:Int):DogBreed


    @Query("delete from dogbreed")
    suspend fun deleteAllDogs()
}