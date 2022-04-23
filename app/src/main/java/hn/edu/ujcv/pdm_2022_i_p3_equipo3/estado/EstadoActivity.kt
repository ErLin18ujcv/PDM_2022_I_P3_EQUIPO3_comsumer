package hn.edu.ujcv.pdm_2022_i_p3_equipo3.estado

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.EstadoDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.EstadoService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_estado.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EstadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estado)

        btnHomeEstado.setOnClickListener {
            home()
        }

        btnNuevoEstado.setOnClickListener {
            agregarEstado()
        }

        callServiceGetAll()

    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun agregarEstado(){
        val intent = Intent(this, IngresarEstadoActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val estadoService : EstadoService = RestEngine.buildService().create(EstadoService::class.java)
        val result : Call<List<EstadoDataCollectionItem>> = estadoService.listEstados()
        result.enqueue(object : Callback<List<EstadoDataCollectionItem>> {
            override fun onFailure(call: Call<List<EstadoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@EstadoActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<EstadoDataCollectionItem>>,
                response: Response<List<EstadoDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@EstadoActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaEstados.adapter = listAdapter

            }
        })
    }
}