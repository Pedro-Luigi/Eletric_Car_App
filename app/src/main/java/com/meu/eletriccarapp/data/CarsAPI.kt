package com.meu.eletriccarapp.data

import com.meu.eletriccarapp.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsAPI {
    @GET("cars.json")
    fun getAllCars() : Call<List<Car>>



}