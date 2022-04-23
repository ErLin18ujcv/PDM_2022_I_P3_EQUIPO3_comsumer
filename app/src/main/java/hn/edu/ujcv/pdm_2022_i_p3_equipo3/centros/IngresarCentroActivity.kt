package hn.edu.ujcv.pdm_2022_i_p3_equipo3.centros

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.CentroDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.CentroVacunacionService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_ingresar_centro.*
import kotlinx.android.synthetic.main.ingresar_centro_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarCentroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_centro)

        regresarListaRol.setOnClickListener {
            regresarLista()
        }

        btnGuardarCentro.setOnClickListener {
            callServicePostCentro()
        }
    }

    fun regresarLista(){
        val intent = Intent(this, CentroActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServicePostCentro(){
        val fabricante = CentroDataCollectionItem(
            id = null,
            nombre = txtNombreCentro.text.toString(),
            direccion = txtDireccionCentro.text.toString()
        )
        addCentro(fabricante){
            if (it?.id != null){
                Toast.makeText(this,"Centro creado. Id: "+ it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addCentro(centroData: CentroDataCollectionItem, onResult : (CentroDataCollectionItem?) -> Unit){
        val centroService : CentroVacunacionService = RestEngine.buildService().create(CentroVacunacionService::class.java)
        val result : Call<CentroDataCollectionItem> = centroService.addCentro(centroData)
        result.enqueue(object : Callback<CentroDataCollectionItem> {
            override fun onFailure(call: Call<CentroDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<CentroDataCollectionItem>,
                response: Response<CentroDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedCentro = response.body()!!
                    onResult(addedCentro)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarCentroActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarCentroActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}