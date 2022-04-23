package hn.edu.ujcv.pdm_2022_i_p3_equipo3.confirmados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.ConfirmadosDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.ConfirmadosService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_confirmados.*
import kotlinx.android.synthetic.main.activity_sintomas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmadosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmados)
        btnHomeLab.setOnClickListener {
            home()
        }
        btnNuevoLab.setOnClickListener {
            agregarConfirmado() }

        listaConfirmados.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            var o = listaConfirmados.getItemAtPosition(i)
            val intent = Intent(this,IngresarConfirmadosActivity::class.java)
            intent.putExtra("idConfirmado",o.toString().split(":")[1].split("\t")[0].toInt())
            startActivity(intent)
            finish()
        }

        callServiceGetAll()
    }
    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun agregarConfirmado(){
        val intent = Intent(this, IngresarConfirmadosActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val confirmadosService : ConfirmadosService = RestEngine.buildService().create(ConfirmadosService::class.java)
        val result : Call<List<ConfirmadosDataCollectionItem>> = confirmadosService.listConfirmados()
        result.enqueue(object : Callback<List<ConfirmadosDataCollectionItem>> {
            override fun onFailure(call: Call<List<ConfirmadosDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ConfirmadosActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<ConfirmadosDataCollectionItem>>,
                response: Response<List<ConfirmadosDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@ConfirmadosActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaConfirmados.adapter = listAdapter

            }
        })
    }
}