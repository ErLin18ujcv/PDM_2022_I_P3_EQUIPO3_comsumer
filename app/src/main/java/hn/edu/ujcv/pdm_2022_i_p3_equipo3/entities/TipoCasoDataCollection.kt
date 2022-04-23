package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities


class TipoCasoDataCollection: ArrayList<TipoCasoDataCollectionItem>()

data class TipoCasoDataCollectionItem(
    val id : Int?,
    val tipo : String
){
    override fun toString(): String {
        return "Id:$id\t\tNombre:$tipo"
    }
}