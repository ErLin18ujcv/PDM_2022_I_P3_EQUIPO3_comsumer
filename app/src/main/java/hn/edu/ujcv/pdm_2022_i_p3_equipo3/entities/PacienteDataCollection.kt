package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

import java.time.LocalDate

class PacienteDataCollection : ArrayList<PacienteDataCollectionItem>()

data class PacienteDataCollectionItem(
    val id : Int?,
    val tipoDocumento : Int,
    val colonia : Int,
    val nombre : String,
    val apellido : String,
    val direccion : String,
    val telefono : String,
    val correo : String,
    val fechaNacimiento : String,
    val documento : String
){
    override fun toString(): String {
        return "Id:$id\t\tNombre:$nombre $apellido"
    }
}