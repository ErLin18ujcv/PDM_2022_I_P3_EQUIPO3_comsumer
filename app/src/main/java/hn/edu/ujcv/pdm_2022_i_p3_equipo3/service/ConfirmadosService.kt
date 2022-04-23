package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.ConfirmadosDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface ConfirmadosService {
    @GET("confirmados")
    fun listConfirmados() : Call<List<ConfirmadosDataCollectionItem>>

    @GET("confirmados/id/{id}")
    fun getConfirmadoById(@Path("id") id : Int) : Call<ConfirmadosDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("confirmados/addConfirmado")
    fun addConfirmado(@Body confirmadoData : ConfirmadosDataCollectionItem) : Call<ConfirmadosDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("confirmados")
    fun updateConfirmado(@Body confirmadoData: ConfirmadosDataCollectionItem) : Call<ConfirmadosDataCollectionItem>
}