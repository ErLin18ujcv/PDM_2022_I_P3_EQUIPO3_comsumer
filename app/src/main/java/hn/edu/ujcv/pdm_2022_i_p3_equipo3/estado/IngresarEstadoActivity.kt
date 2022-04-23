package hn.edu.ujcv.pdm_2022_i_p3_equipo3.estado

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.EstadoDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.EstadoService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_ingresar_estado.*
import kotlinx.android.synthetic.main.ingresar_estado_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarEstadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_estado)

        regresarListaEstado.setOnClickListener {
            regresarLista()
        }

        btnGuardarEstado.setOnClickListener {
            callServicePostEstado()
        }
    }

    fun regresarLista(){
        val intent = Intent(this, EstadoActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServicePostEstado(){
        var estado = EstadoDataCollectionItem(id = null,
            nombre = txtNombreEstado.text.toString(),
        descripcion = txtDescripcionEstado.text.toString())
        addEstado(estado){
            if (it?.id != null){
                Toast.makeText(this,"Estado creado. Id: "+it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addEstado(estadoData: EstadoDataCollectionItem, onResult : (EstadoDataCollectionItem?) -> Unit){
        val estadoService : EstadoService = RestEngine.buildService().create(EstadoService::class.java)
        val result : Call<EstadoDataCollectionItem> = estadoService.addEstado(estadoData)
        result.enqueue(object : Callback<EstadoDataCollectionItem> {
            override fun onFailure(call: Call<EstadoDataCollectionItem>, t: Throwable) {
            onResult(null)
            }

            override fun onResponse(
                call: Call<EstadoDataCollectionItem>,
                response: Response<EstadoDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedEstado = response.body()!!
                    onResult(addedEstado)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarEstadoActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarEstadoActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}