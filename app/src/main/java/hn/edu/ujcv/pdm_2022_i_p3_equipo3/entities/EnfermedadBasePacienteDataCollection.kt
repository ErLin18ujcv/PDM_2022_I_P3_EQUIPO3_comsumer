package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities


class EnfermedadBasePacienteDataCollection : ArrayList<EnfermedadBasePacienteDataCollectionItem>()

data class EnfermedadBasePacienteDataCollectionItem(
    var id : Int?,
    val idPaciente : Int,
    val idEnfermedadBase : Int
){

}