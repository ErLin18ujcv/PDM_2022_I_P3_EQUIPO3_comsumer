package hn.edu.ujcv.pdm_2022_i_p3_equipo3.caso

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.TipoCasoDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.TipoCasoService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.vacuna.VacunasActivity
import kotlinx.android.synthetic.main.activity_ingresar_caso.*
import kotlinx.android.synthetic.main.ingresar_caso_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarCasoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_caso)

        regresarListaLab.setOnClickListener {
            regresarLista()
        }

        btnGuardarCaso.setOnClickListener {
            callServicePostTipoCaso()
        }
    }

    fun regresarLista(){
        val intent = Intent(this, CasoActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServicePostTipoCaso(){
        val fabricante = TipoCasoDataCollectionItem(
            id = null,
            tipo = txtNombreTipoCaso.text.toString()
        )
        addTipoCaso(fabricante){
            if (it?.id != null){
                Toast.makeText(this,"Fabricante creado. Id: "+ it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addTipoCaso(tipoCasoData: TipoCasoDataCollectionItem, onResult : (TipoCasoDataCollectionItem?) -> Unit){
        val tipoCasoService : TipoCasoService = RestEngine.buildService().create(TipoCasoService::class.java)
        val result : Call<TipoCasoDataCollectionItem> = tipoCasoService.addTipoCaso(tipoCasoData)
        result.enqueue(object : Callback<TipoCasoDataCollectionItem> {
            override fun onFailure(call: Call<TipoCasoDataCollectionItem>, t: Throwable) {
            onResult(null)
            }

            override fun onResponse(
                call: Call<TipoCasoDataCollectionItem>,
                response: Response<TipoCasoDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedTipoCaso = response.body()!!
                    onResult(addedTipoCaso)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarCasoActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarCasoActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}