package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

class SintomaDataCollection : ArrayList<SintomaDataCollectionItem>()

data class SintomaDataCollectionItem(
    var id : Int?,
    val gravedad : Int,
    val nombre : String
){
    override fun toString(): String {
        return "Id:$id\t\tNombre:$nombre"
    }
}