package hn.edu.ujcv.pdm_2022_i_p3_equipo3.confirmados

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.ConfirmadosDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.LaboratorioDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.ConfirmadosService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.LaboratorioService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import kotlinx.android.synthetic.main.activity_ingresar_confirmados.*
import kotlinx.android.synthetic.main.ingresar_confirmado_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class IngresarConfirmadosActivity : AppCompatActivity() {
    var idPaciente : Int? = null
    var idConfirmado : Int?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_confirmados)
        regresarListaLab.setOnClickListener {
            regresarLista()
        }

        val txtFechaExamen = findViewById<EditText>(R.id.fechaExamenConfirmado)
        val txtFechaEntregaResultado = findViewById<EditText>(R.id.fechaEntregaResultado)
        val txtFechaFallecido = findViewById<EditText>(R.id.fechaFallecimiento)
        val txtFechaRecuperado = findViewById<EditText>(R.id.fechaRecuperacion)
        txtFechaExamen.setText(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(System.currentTimeMillis()))
        txtFechaEntregaResultado.setText(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(System.currentTimeMillis()))
        txtFechaFallecido.setText(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(System.currentTimeMillis()))
        txtFechaRecuperado.setText(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(System.currentTimeMillis()))
        val calendar = Calendar.getInstance()

        val dateSetListenerFechaExamen = DatePickerDialog.OnDateSetListener { view, anio, mes, dia ->
            calendar.set(Calendar.YEAR, anio)
            calendar.set(Calendar.MONTH, mes)
            calendar.set(Calendar.DAY_OF_MONTH, dia)

            val formato = "yyyy-MM-dd'T'HH:mm:ss"
            val sdf = SimpleDateFormat(formato, Locale.US)
            txtFechaExamen.setText(sdf.format(calendar.time))
        }

        val dateSetListenerFechaEntregaResultado = DatePickerDialog.OnDateSetListener { view, anio, mes, dia ->
            calendar.set(Calendar.YEAR, anio)
            calendar.set(Calendar.MONTH, mes)
            calendar.set(Calendar.DAY_OF_MONTH, dia)

            val formato = "yyyy-MM-dd'T'HH:mm:ss"
            val sdf = SimpleDateFormat(formato, Locale.US)
            txtFechaEntregaResultado.setText(sdf.format(calendar.time))
        }

        val dateSetListenerFechaFallecido = DatePickerDialog.OnDateSetListener { view, anio, mes, dia ->
            calendar.set(Calendar.YEAR, anio)
            calendar.set(Calendar.MONTH, mes)
            calendar.set(Calendar.DAY_OF_MONTH, dia)

            val formato = "yyyy-MM-dd'T'HH:mm:ss"
            val sdf = SimpleDateFormat(formato, Locale.US)
            txtFechaFallecido.setText(sdf.format(calendar.time))
        }

        val dateSetListenerFechaRecuperado = DatePickerDialog.OnDateSetListener { view, anio, mes, dia ->
            calendar.set(Calendar.YEAR, anio)
            calendar.set(Calendar.MONTH, mes)
            calendar.set(Calendar.DAY_OF_MONTH, dia)

            val formato = "yyyy-MM-dd'T'HH:mm:ss"
            val sdf = SimpleDateFormat(formato, Locale.US)
            txtFechaRecuperado.setText(sdf.format(calendar.time))
        }

        btnGuardarConfirmado.setOnClickListener{
            callServicePostConfirmado()
        }


        fechaExamenConfirmado.setOnClickListener {
            DatePickerDialog(
                this, dateSetListenerFechaExamen,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        fechaEntregaResultado.setOnClickListener {
            DatePickerDialog(
                this, dateSetListenerFechaEntregaResultado,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        fechaFallecimiento.setOnClickListener {
            DatePickerDialog(
                this, dateSetListenerFechaFallecido,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        fechaRecuperacion.setOnClickListener {
            DatePickerDialog(
                this, dateSetListenerFechaRecuperado,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        recuperado.setOnCheckedChangeListener { compoundButton, b ->
            if (fallecido.isChecked) {
                Toast.makeText(
                    this,
                    "No puedes activar fallecido y recuperado al mismo tiempo.",
                    Toast.LENGTH_SHORT
                ).show()
                recuperado.isChecked = false
                return@setOnCheckedChangeListener
            }
            if (b) {
                fechaRecuperacionTag.visibility = View.VISIBLE
                fechaRecuperacion.visibility = View.VISIBLE
            } else {
                fechaRecuperacionTag.visibility = View.GONE
                fechaRecuperacion.visibility = View.GONE
            }
        }

        fallecido.setOnCheckedChangeListener { compoundButton, b ->
            if (recuperado.isChecked) {
                Toast.makeText(
                    this,
                    "No puedes activar fallecido y recuperado al mismo tiempo.",
                    Toast.LENGTH_SHORT
                ).show()
                fallecido.isChecked = false
                return@setOnCheckedChangeListener
            }
            if (b) {
                fechaFallecimientoTag.visibility = View.VISIBLE
                fechaFallecimiento.visibility = View.VISIBLE
            } else {
                fechaFallecimientoTag.visibility = View.GONE
                fechaFallecimiento.visibility = View.GONE
            }
        }

        idPaciente = intent.getIntExtra("idPaciente", 0)
        idConfirmado = intent.getIntExtra("idConfirmado",0)
        callServiceGetAllLaboratorios()


        if (idConfirmado != 0){
            callServiceGetConfirmado(idConfirmado!!)
            btnGuardarConfirmado.setText("Actualizar")
        }
        txtIdPacienteConfirmado.setText(idPaciente.toString())

    }

    private fun regresarLista() {
        val intent = Intent(this, ConfirmadosActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServiceGetAllLaboratorios() {
        val laboratorioService: LaboratorioService = RestEngine.buildService().create(
            LaboratorioService::class.java
        )
        val result: Call<List<LaboratorioDataCollectionItem>> =
            laboratorioService.listLaboratorios()
        result.enqueue(object : Callback<List<LaboratorioDataCollectionItem>> {
            override fun onFailure(call: Call<List<LaboratorioDataCollectionItem>>, t: Throwable) {
                Toast.makeText(
                    this@IngresarConfirmadosActivity,
                    "Error${t.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<LaboratorioDataCollectionItem>>,
                response: Response<List<LaboratorioDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(
                    this@IngresarConfirmadosActivity,
                    android.R.layout.simple_list_item_1,
                    response.body()!!
                )
                spinnerLaboratorios.adapter = listAdapter

            }
        })
    }


    private fun callServiceGetConfirmado(idConfirmado: Int) {
        val confirmadoService: ConfirmadosService = RestEngine.buildService().create(ConfirmadosService::class.java)
        val result: Call<ConfirmadosDataCollectionItem> = confirmadoService.getConfirmadoById(idConfirmado)
        result.enqueue(object : Callback<ConfirmadosDataCollectionItem> {
            override fun onFailure(call: Call<ConfirmadosDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@IngresarConfirmadosActivity,
                    "Error${t.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ConfirmadosDataCollectionItem>,
                response: Response<ConfirmadosDataCollectionItem>
            ) {
                for (i in 0 until spinnerLaboratorios.count){
                    if (spinnerLaboratorios.getItemAtPosition(i).toString().split(":")[1].split("\t")[0].toInt() == response.body()!!.idLaboratorio){
                        spinnerLaboratorios.setSelection(i)
                    }
                }
                idPaciente = response.body()!!.idPaciente
                //txtIdPacienteConfirmado.setText(response.body()!!.idPaciente)
                fechaExamenConfirmado.setText(response.body()!!.fechaExamen)
                fechaEntregaResultado.setText(response.body()!!.fechaEntregaResultado)
                fechaRecuperacion.setText(response.body()!!.fechaRecuperacion)
                fechaFallecimiento.setText(response.body()!!.fechaDeceso)
                recuperado.isChecked = response.body()!!.recuperado
                fallecido.isChecked = response.body()!!.fallecido

            }

        })
    }


    private fun callServicePostConfirmado() {

        if (idConfirmado != 0){
            callServiceUpdateConfirmado()
            return
        }

        var confirmado = ConfirmadosDataCollectionItem(id = null,
            idPaciente = idPaciente!!,
            idLaboratorio = spinnerLaboratorios.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            fechaExamen = fechaExamenConfirmado.text.toString(),
            fechaEntregaResultado = fechaEntregaResultado.text.toString(),
            fechaRecuperacion =  if(recuperado.isChecked)fechaRecuperacion.text.toString() else null,
            fechaDeceso =  if(fallecido.isChecked)fechaFallecimiento.text.toString() else null,
            recuperado = recuperado.isChecked,
            fallecido = fallecido.isChecked)
        addConfirmado(confirmado){
            if (it?.id != null){
                Toast.makeText(this,"Paciente confirmado creado "+it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addConfirmado(confirmadoData: ConfirmadosDataCollectionItem, onResult : (ConfirmadosDataCollectionItem?) -> Unit){
        val estadoService : ConfirmadosService = RestEngine.buildService().create(ConfirmadosService::class.java)
        val result : Call<ConfirmadosDataCollectionItem> = estadoService.addConfirmado(confirmadoData)
        result.enqueue(object : Callback<ConfirmadosDataCollectionItem> {
            override fun onFailure(call: Call<ConfirmadosDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<ConfirmadosDataCollectionItem>,
                response: Response<ConfirmadosDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedConfirmado = response.body()!!
                    onResult(addedConfirmado)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarConfirmadosActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarConfirmadosActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun callServiceUpdateConfirmado() {

        var confirmado = ConfirmadosDataCollectionItem(id = idConfirmado,
            idPaciente = idPaciente!!,
            idLaboratorio = spinnerLaboratorios.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            fechaExamen = fechaExamenConfirmado.text.toString(),
            fechaEntregaResultado = fechaEntregaResultado.text.toString(),
            fechaRecuperacion =  if(recuperado.isChecked)fechaRecuperacion.text.toString() else null,
            fechaDeceso =  if(fallecido.isChecked)fechaFallecimiento.text.toString() else null,
            recuperado = recuperado.isChecked,
            fallecido = fallecido.isChecked)
        updateConfirmado(confirmado){
            if (it?.id != null){
                Toast.makeText(this,"Paciente confirmado actualizado "+it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateConfirmado(confirmadoData: ConfirmadosDataCollectionItem, onResult : (ConfirmadosDataCollectionItem?) -> Unit){
        val estadoService : ConfirmadosService = RestEngine.buildService().create(ConfirmadosService::class.java)
        val result : Call<ConfirmadosDataCollectionItem> = estadoService.updateConfirmado(confirmadoData)
        result.enqueue(object : Callback<ConfirmadosDataCollectionItem> {
            override fun onFailure(call: Call<ConfirmadosDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<ConfirmadosDataCollectionItem>,
                response: Response<ConfirmadosDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedConfirmado = response.body()!!
                    onResult(addedConfirmado)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarConfirmadosActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarConfirmadosActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}