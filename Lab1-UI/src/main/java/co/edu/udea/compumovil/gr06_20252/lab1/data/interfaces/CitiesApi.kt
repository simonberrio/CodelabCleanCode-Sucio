package co.edu.udea.compumovil.gr06_20252.lab1.data.interfaces

import co.edu.udea.compumovil.gr06_20252.lab1.data.model.CityResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {
    @GET("searchJSON")
    suspend fun getCities(
        @Query("name_startsWith") query: String,
        @Query("country") country: String = "CO",
        @Query("maxRows") maxRows: Int = 10,
        @Query("username") username: String    ): CityResponse
}