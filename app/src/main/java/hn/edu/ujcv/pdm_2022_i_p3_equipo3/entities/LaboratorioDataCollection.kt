package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

class LaboratorioDataCollection : ArrayList<LaboratorioDataCollectionItem>()

data class LaboratorioDataCollectionItem(
    val id : Int?,
    val nombre : String,
    val direccion : String
){
    override fun toString(): String {
        return "Id:$id\t\tNombre:$nombre"
    }
}