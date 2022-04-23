package hn.edu.ujcv.pdm_2022_i_p3_equipo3.sintomas

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.SintomaDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.SintomasService
import kotlinx.android.synthetic.main.activity_sintomas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SintomasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sintomas)

        btnHomeSintomas.setOnClickListener {
            home()
        }

        btnNuevoSintoma.setOnClickListener {
            agregarSintoma()
        }

        callServiceGetAll()
    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun agregarSintoma(){
        val intent = Intent(this, IngresarSintomaActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val sintomasService : SintomasService = RestEngine.buildService().create(SintomasService::class.java)
        val result : Call<List<SintomaDataCollectionItem>> = sintomasService.listSintomas()
        result.enqueue(object : Callback<List<SintomaDataCollectionItem>> {
            override fun onFailure(call: Call<List<SintomaDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@SintomasActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<SintomaDataCollectionItem>>,
                response: Response<List<SintomaDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@SintomasActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaSintomas.adapter = listAdapter

            }
        })
    }
}