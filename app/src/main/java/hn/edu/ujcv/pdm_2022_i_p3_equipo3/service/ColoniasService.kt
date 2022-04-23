package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.ColoniaDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface ColoniasService {
    @GET("colonias")
    fun listColonias() : Call<List<ColoniaDataCollectionItem>>

    @GET("colonias/id/{id}")
    fun getColoniaById(@Path("id") id : Int) : Call<ColoniaDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("colonias/addColonia")
    fun addColonias(@Body coloniaData : ColoniaDataCollectionItem) : Call<ColoniaDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("colonias")
    fun updateColonia(@Body coloniaData: ColoniaDataCollectionItem) : Call<ColoniaDataCollectionItem>
}