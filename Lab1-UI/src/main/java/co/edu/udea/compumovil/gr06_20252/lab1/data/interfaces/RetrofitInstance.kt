package co.edu.udea.compumovil.gr06_20252.lab1.data.interfaces

import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.geonames.org/"

    // Retrofit singleton
    val api: CitiesApi by lazy {
        Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CitiesApi::class.java)
    }
}