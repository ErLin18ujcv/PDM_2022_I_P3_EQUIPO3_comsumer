package hn.edu.ujcv.pdm_2022_i_p3_equipo3.enfermedad

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.EnfermedadDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.EnfermedadesBaseService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_enfermedades.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnfermedadesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enfermedades)

        btnHomeEnfermedades.setOnClickListener {
            home()
        }

        btnNuevaEnfermedad.setOnClickListener {
            agregarEnfermedad()
        }

        callServiceGetAll()
    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun agregarEnfermedad(){
        val intent = Intent(this, IngresarEnfermedadesActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val pacienteService : EnfermedadesBaseService = RestEngine.buildService().create(EnfermedadesBaseService::class.java)
        val result : Call<List<EnfermedadDataCollectionItem>> = pacienteService.listEnfermedades()
        result.enqueue(object : Callback<List<EnfermedadDataCollectionItem>> {
            override fun onFailure(call: Call<List<EnfermedadDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@EnfermedadesActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<EnfermedadDataCollectionItem>>,
                response: Response<List<EnfermedadDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@EnfermedadesActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaEnfermedades.adapter = listAdapter

            }
        })
    }
}