package hn.edu.ujcv.pdm_2022_i_p3_equipo3.colonias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.ColoniaDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.ColoniasService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_ingresar_colonia.*
import kotlinx.android.synthetic.main.ingresar_colonia_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarColoniaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_colonia)
        regresarListaLab.setOnClickListener {
            regresarLista()
        }

        btnGuardarColonia.setOnClickListener{
            callServicePostColonia()
        }

    }
    fun regresarLista(){
        val intent = Intent(this, ColoniasActivity::class.java)
        startActivity(intent)
        finish()
    }



    private fun callServicePostColonia(){
        var colonia = ColoniaDataCollectionItem(id = null,
            nombre = txtNombreColonia.text.toString(),
            activo =true)
        addColonia(colonia){
            if (it?.id != null){
                Toast.makeText(this@IngresarColoniaActivity,"Colonia creada. Id:"+it?.id,Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this@IngresarColoniaActivity,"Error",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addColonia(coloniaData: ColoniaDataCollectionItem, onResult : (ColoniaDataCollectionItem?) -> Unit){
        val coloniasService : ColoniasService = RestEngine.buildService().create(ColoniasService::class.java)
        val result : Call<ColoniaDataCollectionItem> = coloniasService.addColonias(coloniaData)
        result.enqueue(object : Callback<ColoniaDataCollectionItem>{
            override fun onFailure(call: Call<ColoniaDataCollectionItem>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ColoniaDataCollectionItem>,
                response: Response<ColoniaDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedColonia = response.body()!!
                    onResult(addedColonia)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarColoniaActivity,errorResponse.errorDetails,Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarColoniaActivity,"Fallo al traer el crear y traer el item.",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}