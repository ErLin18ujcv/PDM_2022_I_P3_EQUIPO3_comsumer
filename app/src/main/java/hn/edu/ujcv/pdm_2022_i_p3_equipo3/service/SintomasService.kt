package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.SintomaDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface SintomasService {
    @GET("sintoma")
    fun listSintomas() : Call<List<SintomaDataCollectionItem>>
    @GET("sintoma/id/{id}")
    fun getSintomaById(@Path("id") id : Int) : Call<SintomaDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("sintoma/addSintoma")
    fun addSintoma(@Body sintomaData : SintomaDataCollectionItem) : Call<SintomaDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("sintoma")
    fun updateSintoma(@Body sintomaData: SintomaDataCollectionItem) : Call<SintomaDataCollectionItem>
}