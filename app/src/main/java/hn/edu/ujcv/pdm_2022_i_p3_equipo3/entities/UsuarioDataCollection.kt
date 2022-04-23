package hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities

//class UsuarioDataCollection : ArrayList<UsuarioDataCollectionItem>()

data class UsuarioDataCollectionItem(
    val id : Int?,
    val idTipoDocumento : Int,
    val rol : Int,
    val nombre : String,
    val apellido : String,
    val correo : String,
    val direccion : String,
    val telefono : String,
    val fechaNacimiento : String,
    val documento : String,
    val nombreUsuario : String,
    val contrasena : String,
    val fechaCambioContrasena : String,
    val activo : Boolean
){

    override fun toString(): String {
        return "Id:$id\t\tNombre:$nombre $apellido"
    }
}