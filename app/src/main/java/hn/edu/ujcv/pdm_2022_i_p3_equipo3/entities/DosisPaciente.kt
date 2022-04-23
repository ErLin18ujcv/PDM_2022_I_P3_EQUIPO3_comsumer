package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

import java.time.LocalDate

class DosisPacienteCollection : ArrayList<DosisPacienteDataCollectionItem>()

data class DosisPacienteDataCollectionItem(
    val id : Int?,
    val idCentroVacunacion : Int,
    val idPaciente : Int,
    val idFabricante : Int,
    val lote : String,
    val fecha : LocalDate
)