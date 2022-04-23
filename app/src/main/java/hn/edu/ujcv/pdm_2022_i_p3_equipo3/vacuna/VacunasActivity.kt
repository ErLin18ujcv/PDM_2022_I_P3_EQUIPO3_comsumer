package hn.edu.ujcv.pdm_2022_i_p3_equipo3.vacuna

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.FabricanteDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.FabricanteVacunaService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_colonias.*
import kotlinx.android.synthetic.main.activity_vacunas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VacunasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacunas)

        btnHomeVacunas.setOnClickListener {
            home()
        }

        btnNuevaVacuna.setOnClickListener {
            agregarVacuna()
        }

        callServiceGetAll()

    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun agregarVacuna(){
        val intent = Intent(this, IngresarVacunaActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val fabricantesService : FabricanteVacunaService = RestEngine.buildService().create(FabricanteVacunaService::class.java)
        val result : Call<List<FabricanteDataCollectionItem>> = fabricantesService.listFabricantes()
        result.enqueue(object : Callback<List<FabricanteDataCollectionItem>> {
            override fun onFailure(call: Call<List<FabricanteDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@VacunasActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<FabricanteDataCollectionItem>>,
                response: Response<List<FabricanteDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@VacunasActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaFabricantes.adapter = listAdapter

            }
        })
    }

}