package hn.edu.ujcv.pdm_2022_i_p3_equipo3.documento

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.TipoDocumentoDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.TipoDocumentoService
import kotlinx.android.synthetic.main.activity_documento.*
import kotlinx.android.synthetic.main.activity_sintomas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DocumentoActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documento)

        btnHomeDocumento.setOnClickListener {
            home()
        }

        btnNuevoDocumento.setOnClickListener {
            agregarDocumento()
        }

        callServiceGetAll()
    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun agregarDocumento(){
        val intent = Intent(this, IngresarDocumentoActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val documentosService : TipoDocumentoService = RestEngine.buildService().create(TipoDocumentoService::class.java)
        val result : Call<List<TipoDocumentoDataCollectionItem>> = documentosService.listTiposDocumento()
        result.enqueue(object : Callback<List<TipoDocumentoDataCollectionItem>> {
            override fun onFailure(call: Call<List<TipoDocumentoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@DocumentoActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<TipoDocumentoDataCollectionItem>>,
                response: Response<List<TipoDocumentoDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@DocumentoActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaDocumentos.adapter = listAdapter

            }
        })
    }
}