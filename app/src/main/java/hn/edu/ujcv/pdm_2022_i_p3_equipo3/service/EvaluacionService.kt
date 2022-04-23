package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.EvaluacionDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface EvaluacionService {
    @GET("evaluacion")
    fun listEvaluaciones() : Call<List<EvaluacionDataCollectionItem>>
    @GET("evaluacion/id/{id}")
    fun getEvaluacionById(@Path("id") id : Int) : Call<EvaluacionDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("evaluacion/addEvaluacion")
    fun addEvaluacion(@Body evaluacionData : EvaluacionDataCollectionItem) : Call<EvaluacionDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("evaluacion")
    fun updateEvaluacion(@Body evaluacionData: EvaluacionDataCollectionItem) : Call<EvaluacionDataCollectionItem>
}