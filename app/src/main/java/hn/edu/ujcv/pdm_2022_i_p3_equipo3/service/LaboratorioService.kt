package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.LaboratorioDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface LaboratorioService {
    @GET("laboratorio")
    fun listLaboratorios() : Call<List<LaboratorioDataCollectionItem>>
    @GET("laboratorio/id/{id}")
    fun getLaboratorioById(@Path("id") id : Int) : Call<LaboratorioDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("laboratorio/addLaboratorio")
    fun addLaboratorio(@Body tipoCasoData : LaboratorioDataCollectionItem) : Call<LaboratorioDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("laboratorio")
    fun updateLaboratorio(@Body tipoCasoData: LaboratorioDataCollectionItem) : Call<LaboratorioDataCollectionItem>
}