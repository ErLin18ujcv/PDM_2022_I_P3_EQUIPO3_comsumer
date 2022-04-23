package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.PacienteDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface PacienteService {
    @GET("paciente")
    fun listPacientes() : Call<List<PacienteDataCollectionItem>>
    @GET("paciente/id/{id}")
    fun getPacienteById(@Path("id") id : Int) : Call<PacienteDataCollectionItem>
    @Headers("Content-Type: application/json")
    @POST("paciente/addPaciente")
    fun addPaciente(@Body pacienteData : PacienteDataCollectionItem) : Call<PacienteDataCollectionItem>
    @Headers("Content-Type: application/json")
    @PUT("paciente")
    fun updatePaciente(@Body pacienteData: PacienteDataCollectionItem) : Call<PacienteDataCollectionItem>
}