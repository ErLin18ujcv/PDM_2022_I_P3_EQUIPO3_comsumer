package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.TipoCasoDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface TipoCasoService {
    @GET("tipo_caso")
    fun listTiposCaso() : Call<List<TipoCasoDataCollectionItem>>
    @GET("tipo_caso/id/{id}")
    fun getTipoCasoById(@Path("id") id : Int) : Call<TipoCasoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("tipo_caso/addTipoCaso")
    fun addTipoCaso(@Body tipoCasoData : TipoCasoDataCollectionItem) : Call<TipoCasoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("tipo_caso")
    fun updateCentro(@Body tipoCasoData: TipoCasoDataCollectionItem) : Call<TipoCasoDataCollectionItem>
}