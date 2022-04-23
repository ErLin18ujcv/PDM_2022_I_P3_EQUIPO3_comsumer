package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

class GravedadDataCollection : ArrayList<GravedadDataCollectionItem>()

data class GravedadDataCollectionItem(
    val id : Int?,
    val nombre : String,
    val descripcion : String
){
    override fun toString(): String {
        return "Id:$id\t\tNombre:$nombre"
    }
}