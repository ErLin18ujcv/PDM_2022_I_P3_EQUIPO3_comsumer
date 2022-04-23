package hn.edu.ujcv.pdm_2022_i_p3_equipo3.laboratorio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.LaboratorioDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.LaboratorioService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_ingresar_caso.*
import kotlinx.android.synthetic.main.ingresar_laboratorio_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarLaboratorioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_laboratorio)
        regresarListaLab.setOnClickListener {
            regresarLista()
        }

        btnGuardarLab.setOnClickListener {
            callServicePostLaboratorio()
        }
    }
    fun regresarLista(){
        val intent = Intent(this, LaboratorioActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun callServicePostLaboratorio(){
        var laboratorio = LaboratorioDataCollectionItem(id = null,
            nombre = txtNombreLab.text.toString(),
            direccion = txtDireccionLab.text.toString())
        addLaboratorio(laboratorio){
            if (it?.id != null){
                Toast.makeText(this,"Laboratorio creado. Id: "+it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addLaboratorio(laboratorioData: LaboratorioDataCollectionItem, onResult : (LaboratorioDataCollectionItem?) -> Unit){
        val estadoService : LaboratorioService = RestEngine.buildService().create(LaboratorioService::class.java)
        val result : Call<LaboratorioDataCollectionItem> = estadoService.addLaboratorio(laboratorioData)
        result.enqueue(object : Callback<LaboratorioDataCollectionItem> {
            override fun onFailure(call: Call<LaboratorioDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<LaboratorioDataCollectionItem>,
                response: Response<LaboratorioDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedLaboratorio = response.body()!!
                    onResult(addedLaboratorio)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarLaboratorioActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarLaboratorioActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}