package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.FabricanteDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface FabricanteVacunaService {
    @GET("fabricante_vacuna")
    fun listFabricantes() : Call<List<FabricanteDataCollectionItem>>

    @GET("fabricante_vacuna/id/{id}")
    fun getFabricanteById(@Path("id") id : Int) : Call<FabricanteDataCollectionItem>

    @GET("fabricante_vacuna/nombre/{nombre}")
    fun getFabricanteByNombre(@Path("nombre") nombre : String) : Call<FabricanteDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("fabricante_vacuna/addFabricanteVacuna")
    fun addFabricante(@Body fabricanteData : FabricanteDataCollectionItem) : Call<FabricanteDataCollectionItem>

    @Headers("Content-Type: application/json")
    @PUT("fabricante_vacuna")
    fun updateFabricante(@Body fabricanteData: FabricanteDataCollectionItem) : Call<FabricanteDataCollectionItem>
}