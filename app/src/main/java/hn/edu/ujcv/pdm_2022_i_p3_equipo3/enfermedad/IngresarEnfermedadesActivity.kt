package hn.edu.ujcv.pdm_2022_i_p3_equipo3.enfermedad

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.EnfermedadDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.EnfermedadesBaseService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_ingresar_enfermedad.*
import kotlinx.android.synthetic.main.ingresar_enfermedad_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarEnfermedadesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_enfermedad)

        regresarListaEnfermedad.setOnClickListener {
            regresarLista()
        }

        btnGuardarEnfermedad.setOnClickListener {
            callServicePostEnfermedadBase()
        }
    }

    fun regresarLista(){
        val intent = Intent(this, EnfermedadesActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServicePostEnfermedadBase(){
        var tipoDocumento = EnfermedadDataCollectionItem(id = null,
            nombre = txtnombreEnfermedad.text.toString(),
        activo = true)
        addEnfermedad(tipoDocumento){
            if (it?.id != null){
                Toast.makeText(this,"Enfermedad creada. Id: "+it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addEnfermedad(enfermedadData: EnfermedadDataCollectionItem, onResult : (EnfermedadDataCollectionItem?) -> Unit){
        val enfermedadService : EnfermedadesBaseService = RestEngine.buildService().create(EnfermedadesBaseService::class.java)
        val result : Call<EnfermedadDataCollectionItem> = enfermedadService.addEnfermedad(enfermedadData)
        result.enqueue(object : Callback<EnfermedadDataCollectionItem> {
            override fun onFailure(call: Call<EnfermedadDataCollectionItem>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<EnfermedadDataCollectionItem>,
                response: Response<EnfermedadDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedEnfermedad = response.body()!!
                    onResult(addedEnfermedad)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarEnfermedadesActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarEnfermedadesActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}