package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities



class ConfirmadosDataCollection : ArrayList<ConfirmadosDataCollectionItem>()

data class ConfirmadosDataCollectionItem(
    val id : Int?,
    val idPaciente : Int,
    val idLaboratorio : Int,
    val fechaExamen : String,
    val fechaEntregaResultado : String,
    val fechaRecuperacion : String?,
    val fechaDeceso : String?,
    val recuperado : Boolean,
    val fallecido : Boolean
){
    override fun toString(): String {
        return "Id:$id\t\tIdPaciente:$idPaciente\tIdLaboratorio:$idLaboratorio"
    }
}