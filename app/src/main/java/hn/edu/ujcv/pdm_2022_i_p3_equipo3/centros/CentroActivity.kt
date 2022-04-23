package hn.edu.ujcv.pdm_2022_i_p3_equipo3.centros

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.CentroDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.CentroVacunacionService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_centro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CentroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centro)

        btnHomeCentro.setOnClickListener {
            home()
        }

        btnNuevoCentro.setOnClickListener {
            agregarCentro()
        }
        callServiceGetAll()
    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun agregarCentro(){
        val intent = Intent(this, IngresarCentroActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val centroService : CentroVacunacionService = RestEngine.buildService().create(CentroVacunacionService::class.java)
        val result : Call<List<CentroDataCollectionItem>> = centroService.listCentros()
        result.enqueue(object : Callback<List<CentroDataCollectionItem>> {
            override fun onFailure(call: Call<List<CentroDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@CentroActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<CentroDataCollectionItem>>,
                response: Response<List<CentroDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@CentroActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaCentros.adapter = listAdapter

            }
        })
    }
}