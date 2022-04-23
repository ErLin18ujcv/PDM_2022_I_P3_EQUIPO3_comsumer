package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

class CentroDataCollection : ArrayList<CentroDataCollectionItem>()

data class CentroDataCollectionItem(
    val id : Int?,
    val nombre : String,
    val direccion : String
){
    override fun toString(): String {
        return "Id:$id\t\tNombre:$nombre"
    }
}