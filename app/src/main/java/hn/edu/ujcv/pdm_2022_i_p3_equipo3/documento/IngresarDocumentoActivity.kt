package hn.edu.ujcv.pdm_2022_i_p3_equipo3.documento

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.TipoDocumentoDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.TipoDocumentoService
import kotlinx.android.synthetic.main.activity_ingresar_documento.*
import kotlinx.android.synthetic.main.ingresar_documento_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngresarDocumentoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_documento)

        regresarListaDocumento.setOnClickListener {
            regresarLista()
        }

        btnGuardarTipoDocumento.setOnClickListener {
            callServicePostDocumento()
        }
    }

    fun regresarLista(){
        val intent = Intent(this, DocumentoActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServicePostDocumento(){
        var tipoDocumento = TipoDocumentoDataCollectionItem(id = null,
            nombre = txtNombreTipoDoc.text.toString())
        addDocumento(tipoDocumento){
            if (it?.id != null){
                Toast.makeText(this,"Tipo de documento creado. Id: "+it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addDocumento(documentoData: TipoDocumentoDataCollectionItem, onResult : (TipoDocumentoDataCollectionItem?) -> Unit){
        val documentoService : TipoDocumentoService = RestEngine.buildService().create(TipoDocumentoService::class.java)
        val result : Call<TipoDocumentoDataCollectionItem> = documentoService.addTipoDocumento(documentoData)
        result.enqueue(object : Callback<TipoDocumentoDataCollectionItem> {
            override fun onFailure(call: Call<TipoDocumentoDataCollectionItem>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<TipoDocumentoDataCollectionItem>,
                response: Response<TipoDocumentoDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedDocumento = response.body()!!
                    onResult(addedDocumento)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarDocumentoActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarDocumentoActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}