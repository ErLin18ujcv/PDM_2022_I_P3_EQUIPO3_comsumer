package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

import java.time.LocalDateTime

class EvaluacionDataCollection : ArrayList<EvaluacionDataCollectionItem>()

data class EvaluacionDataCollectionItem(
    val id : Int?,
    val idUsuario : Int,
    val idPaciente : Int,
    val idEstado : Int,
    val idTipoCaso : Int,
    val fechaHora : String,
    val comentarios : String
){
    override fun toString(): String {
        return "Id:$id"
    }
}