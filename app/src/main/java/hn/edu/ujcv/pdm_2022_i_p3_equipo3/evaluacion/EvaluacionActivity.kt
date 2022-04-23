package hn.edu.ujcv.pdm_2022_i_p3_equipo3.evaluacion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.confirmados.IngresarConfirmadosActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.EvaluacionDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.EvaluacionService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_confirmados.*
import kotlinx.android.synthetic.main.activity_evaluacion.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EvaluacionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluacion)

        btnHomeEvaluacion.setOnClickListener {
            home()
        }

        btnNuevaEvaluacion.setOnClickListener {
            agregarEvaluacion()
        }

        callServiceGetAll()

        listaEvaluaciones.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            var o = listaEvaluaciones.getItemAtPosition(i)
            val intent = Intent(this, IngresarEvaluacionActivity::class.java)
            intent.putExtra("idEvaluacion",o.toString().split(":")[1].split("\t")[0].toInt())
            startActivity(intent)
            finish()
        }

    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun agregarEvaluacion(){
        val intent = Intent(this, IngresarEvaluacionActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val evaluacionService : EvaluacionService = RestEngine.buildService().create(EvaluacionService::class.java)
        val result : Call<List<EvaluacionDataCollectionItem>> = evaluacionService.listEvaluaciones()
        result.enqueue(object : Callback<List<EvaluacionDataCollectionItem>> {
            override fun onFailure(call: Call<List<EvaluacionDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@EvaluacionActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<EvaluacionDataCollectionItem>>,
                response: Response<List<EvaluacionDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@EvaluacionActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaEvaluaciones.adapter = listAdapter

            }
        })
    }
}