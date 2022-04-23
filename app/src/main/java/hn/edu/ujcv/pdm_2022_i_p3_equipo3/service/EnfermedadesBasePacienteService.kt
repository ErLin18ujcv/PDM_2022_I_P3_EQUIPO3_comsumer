package hn.edu.ujcv.pdm_2022_i_p3_equipo3.service

import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.EnfermedadBasePacienteDataCollectionItem
import retrofit2.Call
import retrofit2.http.*

interface EnfermedadesBasePacienteService {
    @GET("enfermedad_base_paciente")
    fun listEnfermedades() : Call<List<EnfermedadBasePacienteDataCollectionItem>>

    @GET("enfermedad_base_paciente/id/{id}")
    fun getEnfermedadById(@Path("id") id : Int) : Call<EnfermedadBasePacienteDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("enfermedad_base_paciente/addEnfermedadBasePaciente")
    fun addEnfermedad(@Body enfermedadData : EnfermedadBasePacienteDataCollectionItem) : Call<EnfermedadBasePacienteDataCollectionItem>

    @Headers("Content-Type: application/json")
    @POST("enfermedad_base_paciente/addEnfermedadBasePaciente")
    fun addEnfermedades(@Body enfermedadesData : List<EnfermedadBasePacienteDataCollectionItem>) : Call<List<EnfermedadBasePacienteDataCollectionItem>>

    @DELETE("enfermedad_base_paciente/id/")
    fun updateEnfermedad(@Body idEnfermedadBasePaciente: Int)
}