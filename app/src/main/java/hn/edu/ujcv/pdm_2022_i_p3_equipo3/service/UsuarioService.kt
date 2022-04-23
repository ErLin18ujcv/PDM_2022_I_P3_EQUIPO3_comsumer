package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.UsuarioDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface UsuarioService {
    @GET("usuario")
    fun listUsuarios() : Call<List<UsuarioDataCollectionItem>>
    @GET("usuario/id/{id}")
    fun getUsuarioById(@Path("id") id : Int) : Call<UsuarioDataCollectionItem>
    @GET("usuario/nombreUsuario/{nombreUsuario}")
    fun getUsuarioByNombreUsuario(@Path("nombreUsuario") nombreUsuario : String) : Call<UsuarioDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("usuario/addUsuario")
    fun addUsuario(@Body usuarioData : UsuarioDataCollectionItem) : Call<UsuarioDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("usuario")
    fun updateUsuario(@Body usuarioData: UsuarioDataCollectionItem) : Call<UsuarioDataCollectionItem>
}