package hn.edu.ujcv.pdm_2022_i_p3_equipo3.paciente

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.PacienteDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.PacienteService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_pacientes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PacientesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacientes)

        btnHomePaciente.setOnClickListener {
            home()
        }

        btnNuevoPaciente.setOnClickListener {
            agregarPaciente()
        }

        callServiceGetAll()
    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun agregarPaciente(){
        val intent = Intent(this, IngresarPacienteActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val pacienteService : PacienteService = RestEngine.buildService().create(PacienteService::class.java)
        val result : Call<List<PacienteDataCollectionItem>> = pacienteService.listPacientes()
        result.enqueue(object : Callback<List<PacienteDataCollectionItem>> {
            override fun onFailure(call: Call<List<PacienteDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@PacientesActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<PacienteDataCollectionItem>>,
                response: Response<List<PacienteDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@PacientesActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaPacientes.adapter = listAdapter

            }
        })
    }
}