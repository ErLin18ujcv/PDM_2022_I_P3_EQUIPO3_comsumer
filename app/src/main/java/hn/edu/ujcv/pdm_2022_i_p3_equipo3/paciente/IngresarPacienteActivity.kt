package hn.edu.ujcv.pdm_2022_i_p3_equipo3.paciente

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.ColoniaDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.PacienteDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.TipoDocumentoDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.ColoniasService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.PacienteService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.TipoDocumentoService
import kotlinx.android.synthetic.main.activity_ingresar_paciente.*
import kotlinx.android.synthetic.main.ingresar_paciente_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class IngresarPacienteActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_paciente)

        val txtFecha = findViewById<EditText>(R.id.fechaNacimientoPaciente)
        txtFecha.setText(SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()))
        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, anio, mes, dia ->
            calendar.set(Calendar.YEAR,anio)
            calendar.set(Calendar.MONTH,mes)
            calendar.set(Calendar.DAY_OF_MONTH,dia)

            val formato = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(formato, Locale.US)
            txtFecha.setText(sdf.format(calendar.time))
        }


        fechaNacimientoPaciente.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        regresarListaPaciente.setOnClickListener {
            regresarLista()
        }

        btnGuardarPaciente.setOnClickListener {
            callServicePostPaciente()
        }

        callServiceGetAllColonias()
        callServiceGetAllDocumentos()

    }

    fun regresarLista(){
        val intent = Intent(this, PacientesActivity::class.java)
        startActivity(intent)
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callServicePostPaciente(){

        var fecha = LocalDate.parse(stringDateEspToStringDateSql(fechaNacimientoPaciente.text.toString()))

        var paciente = PacienteDataCollectionItem(id = null,
            tipoDocumento = spinnerTiposDocPaciente.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            colonia = spinnerColonias.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            nombre = txtnombrePaciente.text.toString(),
            apellido = txtApellidosPaciente.text.toString(),
            direccion = direccionPaciente.text.toString(),
            telefono = txtTelefonoPaciente.text.toString(),
            correo = txtCorreoPaciente.text.toString(),
            fechaNacimiento = fecha.format(DateTimeFormatter.ISO_LOCAL_DATE),
        documento = txtNumDocPaciente.text.toString())
        addPaciente(paciente){
            if (it?.id != null){
                Toast.makeText(this,"Paciente creado. Id: "+it.id, Toast.LENGTH_SHORT).show()
                regresarLista()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addPaciente(pacienteData: PacienteDataCollectionItem, onResult : (PacienteDataCollectionItem?) -> Unit){
        val estadoService : PacienteService = RestEngine.buildService().create(PacienteService::class.java)
        val result : Call<PacienteDataCollectionItem> = estadoService.addPaciente(pacienteData)
        result.enqueue(object : Callback<PacienteDataCollectionItem> {
            override fun onFailure(call: Call<PacienteDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<PacienteDataCollectionItem>,
                response: Response<PacienteDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedPaciente = response.body()!!
                    onResult(addedPaciente)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarPacienteActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarPacienteActivity,"Fallo al  crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun callServiceGetAllDocumentos(){
        val documentosService : TipoDocumentoService = RestEngine.buildService().create(
            TipoDocumentoService::class.java)
        val result : Call<List<TipoDocumentoDataCollectionItem>> = documentosService.listTiposDocumento()
        result.enqueue(object : Callback<List<TipoDocumentoDataCollectionItem>> {
            override fun onFailure(call: Call<List<TipoDocumentoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarPacienteActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<TipoDocumentoDataCollectionItem>>,
                response: Response<List<TipoDocumentoDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@IngresarPacienteActivity,android.R.layout.simple_list_item_1, response.body()!!)
                spinnerTiposDocPaciente.adapter = listAdapter

            }
        })
    }

    private fun callServiceGetAllColonias(){
        val coloniasService : ColoniasService = RestEngine.buildService().create(ColoniasService::class.java)
        val result : Call<List<ColoniaDataCollectionItem>> = coloniasService.listColonias()
        result.enqueue(object : Callback<List<ColoniaDataCollectionItem>> {
            override fun onFailure(call: Call<List<ColoniaDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarPacienteActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<ColoniaDataCollectionItem>>,
                response: Response<List<ColoniaDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@IngresarPacienteActivity,android.R.layout.simple_list_item_1, response.body()!!)
                spinnerColonias.adapter = listAdapter

            }
        })
    }

    private fun stringDateEspToStringDateSql(formatoEsp : String) : String {
        var fechaSql = formatoEsp.split("/")[2] + "-" + formatoEsp.split("/")[1] + "-" + formatoEsp.split("/")[0]
        return fechaSql
    }



}