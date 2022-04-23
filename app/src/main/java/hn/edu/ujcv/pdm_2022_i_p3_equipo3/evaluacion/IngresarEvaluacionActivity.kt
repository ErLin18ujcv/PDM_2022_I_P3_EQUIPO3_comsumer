package hn.edu.ujcv.pdm_2022_i_p3_equipo3.evaluacion

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.UsuarioActual
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.confirmados.IngresarConfirmadosActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.*
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.*
import kotlinx.android.synthetic.main.activity_ingresar_evaluacion.*
import kotlinx.android.synthetic.main.ingresar_confirmado_content.*
import kotlinx.android.synthetic.main.ingresar_evaluacion_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class IngresarEvaluacionActivity : AppCompatActivity() {

    var idEvaluacion = 0

    var enfermedades : HashMap<Int,EnfermedadDataCollectionItem> = hashMapOf()
    var sintomas : HashMap<Int,SintomaDataCollectionItem> = hashMapOf()
    var listaEnfermedades = ArrayList<EnfermedadDataCollectionItem>()
    var listaSintomas = ArrayList<SintomaDataCollectionItem>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_evaluacion)

        regresarListaEvaluacion.setOnClickListener {
            regresarLista()
        }

        idEvaluacion = intent.getIntExtra("idEvaluacion",0)

        callServiceGetAllSintomas()
        callServiceGetAllEnfermedades()
        callServiceGetAllPacientes()
        callServiceGetAllEstados()
        callServiceGetAllTipoCaso()

        usuarioActual.text = UsuarioActual.usuarioLogueado!!.nombre + " " + UsuarioActual.usuarioLogueado!!.apellido
        horaActualSistema.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(System.currentTimeMillis())

        btnGuardarEvaluacion.setOnClickListener{
            callServicePostEvaluacion()
        }

        anadirSintoma.setOnClickListener{
            anadirSintoma()
        }

        anadirEnfermedadBase.setOnClickListener{
            anadirEnfermedadBase()
        }

        if (idEvaluacion != 0){
            btnGuardarEvaluacion.setText("Actualizar")
            callServiceGetEvaluacion(idEvaluacion)
        }

    }

    private fun anadirSintoma(){
        var id = spinnerSintomas.selectedItem.toString().split(":")[1].split("\t")[0].toInt()
        listaSintomas.add(SintomaDataCollectionItem(id = id,
            gravedad = sintomas.get(id)!!.gravedad,
            nombre = sintomas.get(id)!!.nombre))

        val listAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, listaSintomas)
        listSintomas.adapter = listAdapter
    }

    private fun anadirEnfermedadBase(){
        var id = spinnerEnfermedades.selectedItem.toString().split(":")[1].split("\t")[0].toInt()
        listaEnfermedades.add(
            EnfermedadDataCollectionItem(id = id,
            nombre = enfermedades.get(id)!!.nombre,
            activo = enfermedades.get(id)!!.activo)
        )
        val listAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, listaEnfermedades)
        listEnfermedades.adapter = listAdapter
    }

    fun regresarLista(){
        val intent = Intent(this, EvaluacionActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServiceGetAllSintomas(){
        val sintomasService : SintomasService = RestEngine.buildService().create(SintomasService::class.java)
        val result : Call<List<SintomaDataCollectionItem>> = sintomasService.listSintomas()
        result.enqueue(object : Callback<List<SintomaDataCollectionItem>> {
            override fun onFailure(call: Call<List<SintomaDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarEvaluacionActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<SintomaDataCollectionItem>>,
                response: Response<List<SintomaDataCollectionItem>>
            ) {
                for (item in response.body()!!){
                    sintomas.put(item.id!!,item)
                }
                val listAdapter = ArrayAdapter(this@IngresarEvaluacionActivity,android.R.layout.simple_list_item_1, response.body()!!)
                spinnerSintomas.adapter = listAdapter
            }
        })
    }

    private fun callServiceGetAllPacientes(){
        val pacientesService : PacienteService = RestEngine.buildService().create(PacienteService::class.java)
        val result : Call<List<PacienteDataCollectionItem>> = pacientesService.listPacientes()
        result.enqueue(object : Callback<List<PacienteDataCollectionItem>> {
            override fun onFailure(call: Call<List<PacienteDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarEvaluacionActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<PacienteDataCollectionItem>>,
                response: Response<List<PacienteDataCollectionItem>>
            ) {
                var arrayListAdapter = ArrayAdapter(
                    this@IngresarEvaluacionActivity,
                    android.R.layout.simple_list_item_1,
                    response.body()!!
                )
                spinnerPacientes.adapter = arrayListAdapter
            }
        })
    }

    private fun callServiceGetAllEstados(){
        val estadosService : EstadoService = RestEngine.buildService().create(EstadoService::class.java)
        val result : Call<List<EstadoDataCollectionItem>> = estadosService.listEstados()
        result.enqueue(object : Callback<List<EstadoDataCollectionItem>> {
            override fun onFailure(call: Call<List<EstadoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarEvaluacionActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<EstadoDataCollectionItem>>,
                response: Response<List<EstadoDataCollectionItem>>
            ) {
                var arrayListAdapter = ArrayAdapter(
                    this@IngresarEvaluacionActivity,
                    android.R.layout.simple_list_item_1,
                    response.body()!!
                )
                spinnerEstados.adapter = arrayListAdapter
            }
        })
    }

    private fun callServiceGetAllTipoCaso(){
        val tiposCasoService : TipoCasoService = RestEngine.buildService().create(TipoCasoService::class.java)
        val result : Call<List<TipoCasoDataCollectionItem>> = tiposCasoService.listTiposCaso()
        result.enqueue(object : Callback<List<TipoCasoDataCollectionItem>> {
            override fun onFailure(call: Call<List<TipoCasoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarEvaluacionActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<TipoCasoDataCollectionItem>>,
                response: Response<List<TipoCasoDataCollectionItem>>
            ) {
                var arrayListAdapter = ArrayAdapter(
                    this@IngresarEvaluacionActivity,
                    android.R.layout.simple_list_item_1,
                    response.body()!!
                )
                spinnerTiposCaso.adapter = arrayListAdapter
            }
        })
    }

    private fun callServiceGetAllEnfermedades(){
        val enfermedadesService : EnfermedadesBaseService = RestEngine.buildService().create(
            EnfermedadesBaseService::class.java)
        val result : Call<List<EnfermedadDataCollectionItem>> = enfermedadesService.listEnfermedades()
        result.enqueue(object : Callback<List<EnfermedadDataCollectionItem>> {
            override fun onFailure(call: Call<List<EnfermedadDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarEvaluacionActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<EnfermedadDataCollectionItem>>,
                response: Response<List<EnfermedadDataCollectionItem>>
            ) {
                for (item in response.body()!!){
                    enfermedades.put(item.id!!,item)
                }
                val listAdapter = ArrayAdapter(this@IngresarEvaluacionActivity,android.R.layout.simple_list_item_1, response.body()!!)
                spinnerEnfermedades.adapter = listAdapter
            }
        })
    }

    private fun addEvaluacion(evaluacionData: EvaluacionDataCollectionItem, onResult : (EvaluacionDataCollectionItem?) -> Unit){
        val evaluacionService : EvaluacionService = RestEngine.buildService().create(EvaluacionService::class.java)
        val result : Call<EvaluacionDataCollectionItem> = evaluacionService.addEvaluacion(evaluacionData)
        result.enqueue(object : Callback<EvaluacionDataCollectionItem>{
            override fun onFailure(call: Call<EvaluacionDataCollectionItem>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<EvaluacionDataCollectionItem>,
                response: Response<EvaluacionDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedEvaluacion = response.body()!!
                    onResult(addedEvaluacion)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarEvaluacionActivity,errorResponse.errorDetails,Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarEvaluacionActivity,"Fallo al traer el crear y traer el item.",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callServicePostEvaluacion(){
        if (idEvaluacion != 0){
            callServicePutEvaluacion()
            return
        }
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val myLdt = LocalDateTime.of(year, month, day, hour, minute)
        var evaluacion = EvaluacionDataCollectionItem(id = null,
            idUsuario = UsuarioActual.usuarioLogueado!!.id!!,
            idPaciente = spinnerPacientes.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            idEstado = spinnerEstados.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            idTipoCaso = spinnerTiposCaso.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            fechaHora = myLdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) ,
            comentarios = comentariosEvaluacion.text.toString())
        addEvaluacion(evaluacion){
            if (it?.id != null){
                Toast.makeText(this,"Evaluacion creada. Id: "+it.id, Toast.LENGTH_SHORT).show()
                //for (i in 0 until listaSintomas.size ){
                //  listaSintomas[i].idEvaluacion = it.id
                //}
                callServicePostSintoma(it.id)
                callServicePostEnfermedad()
                println(it!!.idTipoCaso)
                if (it.idTipoCaso == 2){
                    val intent = Intent(this, IngresarConfirmadosActivity::class.java)
                    intent.putExtra("idPaciente",it.idPaciente)
                    startActivity(intent)
                }
                val intent = Intent(this, EvaluacionActivity::class.java)
                intent.putExtra("idPaciente",it.idPaciente)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callServicePostSintoma(idEvaluacion : Int){
        for (item in listaSintomas){
            var sintoma = SintomasEvaluacionDataCollectionItem(id = null,
                idEvaluacion = idEvaluacion,
                idSintoma =  item.id!!)
            addSintoma(sintoma) {
                if (it?.id != null) {
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun callServicePostEnfermedad(){
        println(spinnerPacientes.selectedItem.toString().split(":")[1].split("\t")[0].toInt())
        for (item in listaEnfermedades){
            var enfermedad = EnfermedadBasePacienteDataCollectionItem(id = null,
                idPaciente = spinnerPacientes.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
                idEnfermedadBase =  item.id!!)
            addEnfermedad(enfermedad) {
                if (it?.id != null) {
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addSintoma(sintomaData: SintomasEvaluacionDataCollectionItem, onResult : (SintomasEvaluacionDataCollectionItem?) -> Unit){
        val sintomaService : SintomasEvaluacionService = RestEngine.buildService().create(SintomasEvaluacionService::class.java)
        val result : Call<SintomasEvaluacionDataCollectionItem> = sintomaService.addSintomaEvluacion(sintomaData)
        result.enqueue(object : Callback<SintomasEvaluacionDataCollectionItem> {
            override fun onFailure(call: Call<SintomasEvaluacionDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<SintomasEvaluacionDataCollectionItem>,
                response: Response<SintomasEvaluacionDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedSintoma = response.body()!!
                    onResult(addedSintoma)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarEvaluacionActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarEvaluacionActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun addEnfermedad(enfermedadData: EnfermedadBasePacienteDataCollectionItem, onResult : (EnfermedadBasePacienteDataCollectionItem?) -> Unit){
        val enfermedadService : EnfermedadesBasePacienteService = RestEngine.buildService().create(EnfermedadesBasePacienteService::class.java)
        val result : Call<EnfermedadBasePacienteDataCollectionItem> = enfermedadService.addEnfermedad(enfermedadData)
        result.enqueue(object : Callback<EnfermedadBasePacienteDataCollectionItem> {
            override fun onFailure(call: Call<EnfermedadBasePacienteDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<EnfermedadBasePacienteDataCollectionItem>,
                response: Response<EnfermedadBasePacienteDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedEnfermedad = response.body()!!
                    onResult(addedEnfermedad)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarEvaluacionActivity,errorResponse.errorDetails, Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarEvaluacionActivity,"Fallo al traer el crear y traer el item.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun callServicePutEvaluacion(){
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val myLdt = LocalDateTime.of(year, month, day, hour, minute)
        var evaluacion = EvaluacionDataCollectionItem(id = idEvaluacion,
            idUsuario = UsuarioActual.usuarioLogueado!!.id!!,
            idPaciente = spinnerPacientes.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            idEstado = spinnerEstados.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            idTipoCaso = spinnerTiposCaso.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
            fechaHora = myLdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) ,
            comentarios = comentariosEvaluacion.text.toString())
        updateEvaluacion(evaluacion){
            if (it?.id != null){
                Toast.makeText(this,"Evaluacion actualizada. Id: "+it.id, Toast.LENGTH_SHORT).show()
                callServicePostSintoma(it.id)
                callServicePostEnfermedad()
                if (it.idTipoCaso == 2){
                    val intent = Intent(this, IngresarConfirmadosActivity::class.java)
                    intent.putExtra("idPaciente",it.idPaciente)
                    startActivity(intent)
                    return@updateEvaluacion
                }
                val intent = Intent(this, EvaluacionActivity::class.java)
                intent.putExtra("idPaciente",it.idPaciente)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateEvaluacion(evaluacionData: EvaluacionDataCollectionItem, onResult : (EvaluacionDataCollectionItem?) -> Unit){
        val evaluacionService : EvaluacionService = RestEngine.buildService().create(EvaluacionService::class.java)
        val result : Call<EvaluacionDataCollectionItem> = evaluacionService.updateEvaluacion(evaluacionData)
        result.enqueue(object : Callback<EvaluacionDataCollectionItem>{
            override fun onFailure(call: Call<EvaluacionDataCollectionItem>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<EvaluacionDataCollectionItem>,
                response: Response<EvaluacionDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedEvaluacion = response.body()!!
                    onResult(addedEvaluacion)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarEvaluacionActivity,errorResponse.errorDetails,Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarEvaluacionActivity,"Fallo al traer el crear y traer el item.",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun callServiceGetEvaluacion(idEvaluacion: Int) {
        val evaluacionService: EvaluacionService = RestEngine.buildService().create(EvaluacionService::class.java)
        val result: Call<EvaluacionDataCollectionItem> = evaluacionService.getEvaluacionById(idEvaluacion)
        result.enqueue(object : Callback<EvaluacionDataCollectionItem> {
            override fun onFailure(call: Call<EvaluacionDataCollectionItem>, t: Throwable) {
                Toast.makeText(
                    this@IngresarEvaluacionActivity,
                    "Error${t.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<EvaluacionDataCollectionItem>,
                response: Response<EvaluacionDataCollectionItem>
            ) {
                for (i in 0 until spinnerPacientes.count){
                    if (spinnerPacientes.getItemAtPosition(i).toString().split(":")[1].split("\t")[0].toInt() == response.body()!!.idPaciente){
                        spinnerPacientes.setSelection(i)
                    }
                }
                horaActualSistema.setText(response.body()!!.fechaHora)
                usuarioActual.setText("Id de usuario: " + response.body()!!.idUsuario)
                spinnerPacientes.isEnabled = false
                for (i in 0 until spinnerEstados.count){
                    if (spinnerEstados.getItemAtPosition(i).toString().split(":")[1].split("\t")[0].toInt() == response.body()!!.idEstado){
                        spinnerEstados.setSelection(i)
                    }
                }
                for (i in 0 until spinnerTiposCaso.count){
                    if (spinnerTiposCaso.getItemAtPosition(i).toString().split(":")[1].split("\t")[0].toInt() == response.body()!!.idTipoCaso){
                        spinnerTiposCaso.setSelection(i)
                    }
                }
                comentariosEvaluacion.setText(response.body()!!.comentarios)

            }

        })
    }
}