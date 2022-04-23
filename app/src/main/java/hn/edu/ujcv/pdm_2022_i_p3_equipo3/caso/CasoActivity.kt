package hn.edu.ujcv.pdm_2022_i_p3_equipo3.caso

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.TipoCasoDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.TipoCasoService
import kotlinx.android.synthetic.main.activity_caso.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CasoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caso)

        btnHomeLab.setOnClickListener {
            home()
        }

        btnNuevoLab.setOnClickListener {
            agregarCaso()
        }

        callServiceGetAll()

    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun agregarCaso(){
        val intent = Intent(this, IngresarCasoActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val casosService : TipoCasoService = RestEngine.buildService().create(TipoCasoService::class.java)
        val result : Call<List<TipoCasoDataCollectionItem>> = casosService.listTiposCaso()
        result.enqueue(object : Callback<List<TipoCasoDataCollectionItem>> {
            override fun onFailure(call: Call<List<TipoCasoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@CasoActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<TipoCasoDataCollectionItem>>,
                response: Response<List<TipoCasoDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@CasoActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaCasos.adapter = listAdapter

            }
        })
    }



}