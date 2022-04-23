package hn.edu.ujcv.pdm_2022_i_p3_equipo3.vacuna


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.FabricanteDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.FabricanteVacunaService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_ingresar_vacuna.*
import kotlinx.android.synthetic.main.ingresar_vacuna_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class IngresarVacunaActivity :AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_vacuna)


        regresarListaVacunas.setOnClickListener {
            regresarLista()
        }

        btnGuardarFabricante.setOnClickListener {
            callServicePostFabricante()
        }
    }

    fun regresarLista(){
        val intent = Intent(this, VacunasActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServicePostFabricante(){
        val fabricante = FabricanteDataCollectionItem(
            id = null,
            nombre = nombreFabricante.text.toString()
        )
        addFabricante(fabricante){
            if (it?.id != null){
                Toast.makeText(this,"Fabricante creado. Id: "+ it.id,Toast.LENGTH_SHORT).show()
                val intent = Intent(this,VacunasActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addFabricante(fabricanteData: FabricanteDataCollectionItem, onResult : (FabricanteDataCollectionItem?) -> Unit){
        val fabricanteService : FabricanteVacunaService = RestEngine.buildService().create(FabricanteVacunaService::class.java)
        val result : Call<FabricanteDataCollectionItem> = fabricanteService.addFabricante(fabricanteData)
        result.enqueue(object : Callback<FabricanteDataCollectionItem> {
            override fun onFailure(call: Call<FabricanteDataCollectionItem>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<FabricanteDataCollectionItem>,
                response: Response<FabricanteDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedColonia = response.body()!!
                    onResult(addedColonia)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarVacunaActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarVacunaActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}

