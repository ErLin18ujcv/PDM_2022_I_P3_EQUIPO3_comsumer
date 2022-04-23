package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.SintomasEvaluacionDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface SintomasEvaluacionService {
    @GET("sintomas_evaluacion")
    fun listSintomasEvaluacion() : Call<List<SintomasEvaluacionDataCollectionItem>>

    @GET("sintomas_evaluacion/id/{id}")
    fun getSintomaEvaluacionById(@Path("id") id : Int) : Call<SintomasEvaluacionDataCollectionItem>

    @GET("sintomas_evaluacion/idEvaluacion/{idEvaluacion}")
    fun getSintomasByIdEvaluacion(@Path("idEvaluacion") id : Int) : Call<SintomasEvaluacionDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("sintomas_evaluacion/addSintomaEvaluacion")
    fun addSintomaEvluacion(@Body enfermedadData : SintomasEvaluacionDataCollectionItem) : Call<SintomasEvaluacionDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("sintomas_evaluacion/addEnfermedad")
    fun addEnfermedades(@Body enfermedadesData : List<SintomasEvaluacionDataCollectionItem>) : Call<List<SintomasEvaluacionDataCollectionItem>>

    @DELETE("sintomas_evaluacion/id/")
    fun updateEnfermedad(@Body idEnfermedadBasePaciente: Int)
}