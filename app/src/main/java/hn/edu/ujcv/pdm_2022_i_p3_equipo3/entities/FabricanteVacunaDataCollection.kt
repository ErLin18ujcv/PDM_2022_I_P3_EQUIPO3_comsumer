package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

class FabricanteDataCollection : ArrayList<FabricanteDataCollectionItem>()

data class FabricanteDataCollectionItem(
    val id : Int?,
    val nombre : String
){
    override fun toString(): String {
        return "Id:$id\t\tNombre:$nombre"
    }
}