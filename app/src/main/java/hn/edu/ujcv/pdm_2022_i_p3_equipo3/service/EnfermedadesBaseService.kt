package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.EnfermedadDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface EnfermedadesBaseService {
    @GET("enfermedad")
    fun listEnfermedades() : Call<List<EnfermedadDataCollectionItem>>
    @GET("enfermedad/id/{id}")
    fun getEnfermedadById(@Path("id") id : Int) : Call<EnfermedadDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("enfermedad/addEnfermedad")
    fun addEnfermedad(@Body enfermedadData : EnfermedadDataCollectionItem) : Call<EnfermedadDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("enfermedad")
    fun updateEnfermedad(@Body enfermedadData: EnfermedadDataCollectionItem) : Call<EnfermedadDataCollectionItem>
}