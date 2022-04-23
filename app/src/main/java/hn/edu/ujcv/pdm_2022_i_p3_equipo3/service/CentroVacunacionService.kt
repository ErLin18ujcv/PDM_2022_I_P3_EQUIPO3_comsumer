package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.CentroDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface CentroVacunacionService {
    @GET("centro")
    fun listCentros() : Call<List<CentroDataCollectionItem>>
    @GET("centro/id/{id}")
    fun getCentroById(@Path("id") id : Int) : Call<CentroDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("centro/addCentro")
    fun addCentro(@Body centroData : CentroDataCollectionItem) : Call<CentroDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("centro")
    fun updateCentro(@Body centroData: CentroDataCollectionItem) : Call<CentroDataCollectionItem>
}