package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.GravedadDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface GravedadService {
    @GET("gravedad")
    fun listGravedad() : Call<List<GravedadDataCollectionItem>>

    @GET("gravedad/id/{id}")
    fun getGravedadbyId(@Path("id") id : Int) : Call<GravedadDataCollectionItem>

    @GET("gravedad/nombre/{nombre}")
    fun getGravedadByNombre(@Path("nombre") nombre : String) : Call<GravedadDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("gravedad/addGravedad")
    fun addGravedad(@Body gravedadData : GravedadDataCollectionItem) : Call<GravedadDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("gravedad")
    fun updateGravedad(@Body gravedadData: GravedadDataCollectionItem) : Call<GravedadDataCollectionItem>
}