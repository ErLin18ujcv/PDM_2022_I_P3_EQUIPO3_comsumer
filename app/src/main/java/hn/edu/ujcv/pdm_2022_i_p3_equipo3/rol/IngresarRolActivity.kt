package hn.edu.ujcv.pdm_2022_i_p3_equipo3.rol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RolDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RolService
import kotlinx.android.synthetic.main.activity_ingresar_rol.*
import kotlinx.android.synthetic.main.ingresar_rol_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarRolActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_rol)
        regresarListaRol.setOnClickListener { regresarLista() }

        btnGuardarRol.setOnClickListener {
            callServicePostRol()
        }
    }
    fun regresarLista(){
        val intent = Intent(this, RolActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServicePostRol(){
        var rol = RolDataCollectionItem(id = null,
            nombre = txtNombreRol.text.toString(),
            descripcion = txtDescripcionRol.text.toString())
        addRol(rol){
            if (it?.id != null){
                Toast.makeText(this,"Laboratorio creado. Id: "+it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addRol(rolData: RolDataCollectionItem, onResult : (RolDataCollectionItem?) -> Unit){
        val rolService : RolService = RestEngine.buildService().create(RolService::class.java)
        val result : Call<RolDataCollectionItem> = rolService.addRol(rolData)
        result.enqueue(object : Callback<RolDataCollectionItem> {
            override fun onFailure(call: Call<RolDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<RolDataCollectionItem>,
                response: Response<RolDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedRol = response.body()!!
                    onResult(addedRol)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarRolActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarRolActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}