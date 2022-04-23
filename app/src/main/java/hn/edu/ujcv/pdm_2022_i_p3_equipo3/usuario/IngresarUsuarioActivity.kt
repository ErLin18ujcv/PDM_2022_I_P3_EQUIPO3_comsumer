package hn.edu.ujcv.pdm_2022_i_p3_equipo3.usuario

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RolDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.TipoDocumentoDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.UsuarioDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RolService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.TipoDocumentoService
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.UsuarioService
import kotlinx.android.synthetic.main.activity_ingresar_usuario.*
import kotlinx.android.synthetic.main.ingresar_usuario_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class IngresarUsuarioActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_usuario)

        println("Hola")

        val txtFecha = findViewById<EditText>(R.id.fechaNacimientoUsuario)
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


        fechaNacimientoUsuario.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        regresarListaUsuario.setOnClickListener {
            regresarLista()
        }

        btnGuardarUsuario.setOnClickListener{

                guardarUsuario()

        }

        callServiceGetAllTipoDocumento()
        callServiceGetAllRoles()
    }

    fun regresarLista(){
        val intent = Intent(this, UsuariosActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callServiceGetAllTipoDocumento(){
        val usuarioService : TipoDocumentoService = RestEngine.buildService().create(
            TipoDocumentoService::class.java)
        val result : Call<List<TipoDocumentoDataCollectionItem>> = usuarioService.listTiposDocumento()
        result.enqueue(object : Callback<List<TipoDocumentoDataCollectionItem>> {
            override fun onFailure(call: Call<List<TipoDocumentoDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarUsuarioActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<TipoDocumentoDataCollectionItem>>,
                response: Response<List<TipoDocumentoDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@IngresarUsuarioActivity,android.R.layout.simple_list_item_1, response.body()!!)
                spinnerTiposDoc.adapter = listAdapter
            }
        })
    }

    private fun callServiceGetAllRoles(){
        val rolesService : RolService = RestEngine.buildService().create(RolService::class.java)
        val result : Call<List<RolDataCollectionItem>> = rolesService.listRol()
        result.enqueue(object : Callback<List<RolDataCollectionItem>> {
            override fun onFailure(call: Call<List<RolDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@IngresarUsuarioActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<RolDataCollectionItem>>,
                response: Response<List<RolDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@IngresarUsuarioActivity,android.R.layout.simple_list_item_1, response.body()!!)
                spinnerRoles.adapter = listAdapter
            }
        })
    }

    private fun addUsuario(usuarioData: UsuarioDataCollectionItem, onResult : (UsuarioDataCollectionItem?) -> Unit){
        val coloniasService : UsuarioService = RestEngine.buildService().create(UsuarioService::class.java)
        val result : Call<UsuarioDataCollectionItem> = coloniasService.addUsuario(usuarioData)
        result.enqueue(object : Callback<UsuarioDataCollectionItem>{
            override fun onFailure(call: Call<UsuarioDataCollectionItem>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<UsuarioDataCollectionItem>,
                response: Response<UsuarioDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedColonia = response.body()!!
                    onResult(addedColonia)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@IngresarUsuarioActivity,errorResponse.errorDetails,Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@IngresarUsuarioActivity,"Fallo al traer el crear y traer el item.",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun guardarUsuario(){
        if (contrasenaUsuario.text.toString() != confirmarContrasena.text.toString()){
            Toast.makeText(this,"Las contrasenas no coinciden.",Toast.LENGTH_SHORT).show()
            Log.d("HOLA",spinnerTiposDoc.selectedItem.toString().split(":")[1].toInt().toString())
            return
        }

        var fecha = LocalDate.parse(stringDateEspToStringDateSql(fechaNacimientoUsuario.text.toString()))

            val usuario  = UsuarioDataCollectionItem(null,
                spinnerTiposDoc.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
                spinnerRoles.selectedItem.toString().split(":")[1].split("\t")[0].toInt(),
                txtNombreUsuario.text.toString(),
                txtApellidosUsuario.text.toString(),
                txtCorreoUsuario.text.toString(),
                direccionUsuario.text.toString(),
                telefonoUsuario.text.toString(),
                fecha.format(DateTimeFormatter.ISO_LOCAL_DATE),
                numDocUsuario.text.toString(),
                cuentaUsuario.text.toString(),
                contrasenaUsuario.text.toString(),
                LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                true
            )

        addUsuario(usuario){
            if (it?.id != null){
                Toast.makeText(this,"Usuario almacenado con id:" +it?.id,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun stringDateEspToStringDateSql(formatoEsp : String) : String {
        var fechaSql = formatoEsp.split("/")[2] + "-" + formatoEsp.split("/")[1] + "-" + formatoEsp.split("/")[0]
        return fechaSql
    }


}