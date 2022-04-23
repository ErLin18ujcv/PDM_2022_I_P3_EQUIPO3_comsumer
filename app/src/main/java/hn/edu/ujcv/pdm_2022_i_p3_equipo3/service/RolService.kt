package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RolDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface RolService {
    @GET("rol")
    fun listRol() : Call<List<RolDataCollectionItem>>
    @GET("rol/id/{id}")
    fun getRolById(@Path("id") id : Int) : Call<RolDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("rol/addRol")
    fun addRol(@Body rolData : RolDataCollectionItem) : Call<RolDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("rol")
    fun updateRol(@Body rolData: RolDataCollectionItem) : Call<RolDataCollectionItem>
}