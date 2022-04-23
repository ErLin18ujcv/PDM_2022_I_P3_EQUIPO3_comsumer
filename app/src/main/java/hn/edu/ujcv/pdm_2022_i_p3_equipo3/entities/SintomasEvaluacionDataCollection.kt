package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

class SintomasEvaluacionDataCollection : ArrayList<SintomasEvaluacionDataCollectionItem>()

data class SintomasEvaluacionDataCollectionItem(
    var id : Int?,
    var idEvaluacion : Int?,
    val idSintoma : Int
)