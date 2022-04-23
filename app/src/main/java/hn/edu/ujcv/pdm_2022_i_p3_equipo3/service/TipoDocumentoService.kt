package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.TipoDocumentoDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface TipoDocumentoService {
    @GET("tipo_documento")
    fun listTiposDocumento() : Call<List<TipoDocumentoDataCollectionItem>>
    @GET("tipo_documento/id/{id}")
    fun getTipoDocumentoById(@Path("id") id : Int) : Call<TipoDocumentoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("tipo_documento/addTipoDocumento")
    fun addTipoDocumento(@Body tipoCasoData : TipoDocumentoDataCollectionItem) : Call<TipoDocumentoDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("tipo_documento")
    fun updateTipoDocumento(@Body tipoCasoData: TipoDocumentoDataCollectionItem) : Call<TipoDocumentoDataCollectionItem>
}