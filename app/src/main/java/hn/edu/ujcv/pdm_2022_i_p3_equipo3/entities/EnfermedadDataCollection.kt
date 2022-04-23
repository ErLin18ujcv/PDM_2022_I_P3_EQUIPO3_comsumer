package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

class EnfermedadDataCollection : ArrayList<EnfermedadDataCollectionItem>()

data class EnfermedadDataCollectionItem(
    val id : Int?,
    val nombre : String,
    val activo : Boolean
){
    override fun toString(): String {
        return "Id:$id\t\tNombre:$nombre"
    }
}