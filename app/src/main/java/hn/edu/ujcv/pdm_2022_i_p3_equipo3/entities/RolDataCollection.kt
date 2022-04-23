package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

class RolDataCollection : ArrayList<RolDataCollectionItem>()

data class RolDataCollectionItem(
    val id : Int?,
    val nombre : String,
    val descripcion : String
){
    override fun toString(): String {
        return "Id:$id\t\tNombre:$nombre\t"
    }
}