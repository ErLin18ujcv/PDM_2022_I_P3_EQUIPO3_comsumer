package hn.edu.ujcv.pdm_2022_i_p3_equipo3.colonias

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.ColoniaDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.ColoniasService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_colonias.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ColoniasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colonias)

        btnHomeLab.setOnClickListener {
            home()
        }
        btnNuevoLab.setOnClickListener{
            agregarColonia() }

        callServiceGetAll()
    }
    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun agregarColonia(){
        val intent = Intent(this, IngresarColoniaActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val coloniasService : ColoniasService = RestEngine.buildService().create(ColoniasService::class.java)
        val result : Call<List<ColoniaDataCollectionItem>> = coloniasService.listColonias()
        result.enqueue(object : Callback<List<ColoniaDataCollectionItem>> {
            override fun onFailure(call: Call<List<ColoniaDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@ColoniasActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<ColoniaDataCollectionItem>>,
                response: Response<List<ColoniaDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@ColoniasActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaColonias.adapter = listAdapter

            }
        })
    }
}