package hn.edu.ujcv.pdm_2022_i_p3_equipo3.laboratorio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.LaboratorioDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.LaboratorioService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_laboratorio.*
import kotlinx.android.synthetic.main.activity_sintomas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaboratorioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laboratorio)
        btnHomeLab.setOnClickListener {
            home()
        }
        btnNuevoLab.setOnClickListener{
            agregarColonia() }

        callServiceGetAll()
    }

    private fun home() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun agregarColonia() {
        val intent = Intent(this, IngresarLaboratorioActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val laboratorioService : LaboratorioService = RestEngine.buildService().create(LaboratorioService::class.java)
        val result : Call<List<LaboratorioDataCollectionItem>> = laboratorioService.listLaboratorios()
        result.enqueue(object : Callback<List<LaboratorioDataCollectionItem>> {
            override fun onFailure(call: Call<List<LaboratorioDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@LaboratorioActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<LaboratorioDataCollectionItem>>,
                response: Response<List<LaboratorioDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@LaboratorioActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaLaboratorios.adapter = listAdapter

            }
        })
    }
}