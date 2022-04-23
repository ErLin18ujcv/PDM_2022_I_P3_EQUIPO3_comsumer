package hn.edu.ujcv.pdm_2022_i_p3_equipo3.sintomas

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.GravedadDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.SintomaDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.GravedadService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.SintomasService
import kotlinx.android.synthetic.main.activity_ingresar_sintoma.*
import kotlinx.android.synthetic.main.ingresar_sintoma_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarSintomaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_sintoma)

        regresarListaSintomas.setOnClickListener {
            regresarLista()
        }

        btnGuardarSintoma.setOnClickListener {
            callServicePostSintoma()
        }

        callServiceGetAllGravedad()
    }

    fun regresarLista(){
        val intent = Intent(this, SintomasActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServicePostSintoma(){
        var sintoma = SintomaDataCollectionItem(id = null,
            gravedad = spinnerGravedad.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            nombre = nombreSintoma.text.toString())
        addSintoma(sintoma){
            if (it?.id != null){
                Toast.makeText(this,"Sintoma creado. Id: "+it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callServiceGetAllGravedad(){
        val gravedadService : GravedadService = RestEngine.buildService().create(
            GravedadService::class.java)
        val result : Call<List<GravedadDataCollectionItem>> = gravedadService.listGravedad()
        result.enqueue(object : Callback<List<GravedadDataCollectionItem>> {
            override fun onFailure(call: Call<List<GravedadDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarSintomaActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<GravedadDataCollectionItem>>,
                response: Response<List<GravedadDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@IngresarSintomaActivity,android.R.layout.simple_list_item_1, response.body()!!)
                spinnerGravedad.adapter = listAdapter

            }
        })
    }

    private fun addSintoma(sintomaData: SintomaDataCollectionItem, onResult : (SintomaDataCollectionItem?) -> Unit){
        val sintomaService : SintomasService = RestEngine.buildService().create(SintomasService::class.java)
        val result : Call<SintomaDataCollectionItem> = sintomaService.addSintoma(sintomaData)
        result.enqueue(object : Callback<SintomaDataCollectionItem> {
            override fun onFailure(call: Call<SintomaDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<SintomaDataCollectionItem>,
                response: Response<SintomaDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedSintoma = response.body()!!
                    onResult(addedSintoma)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarSintomaActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarSintomaActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}