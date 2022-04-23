package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.EstadoDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface EstadoService {
    @GET("estado")
    fun listEstados() : Call<List<EstadoDataCollectionItem>>
    @GET("estado/id/{id}")
    fun getEstadoById(@Path("id") id : Int) : Call<EstadoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("estado/addEstado")
    fun addEstado(@Body estadoData : EstadoDataCollectionItem) : Call<EstadoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("estado")
    fun updateEstado(@Body estadoData: EstadoDataCollectionItem) : Call<EstadoDataCollectionItem>
}