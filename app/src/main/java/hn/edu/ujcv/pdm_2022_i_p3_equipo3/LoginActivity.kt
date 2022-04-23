package hn.edu.ujcv.pdm_2022_i_p3_equipo3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.UsuarioDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.UsuarioService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            login()
        }

    }

    fun login(){
        getUsuarioByNombreUsuario(editTextUsuario.text.toString())
        //Toast.makeText(this,usuarioActual.nombre,Toast.LENGTH_LONG).show()
//
    }

    private fun getUsuarioByNombreUsuario(nombreUsuario : String){
        val usuarioService : UsuarioService = RestEngine.buildService().create(UsuarioService::class.java)
        if (nombreUsuario.isEmpty()){
            Toast.makeText(this@LoginActivity,"Por favor especifica el usuario",Toast.LENGTH_SHORT).show()
            return
        }
        val result : Call<UsuarioDataCollectionItem> = usuarioService.getUsuarioByNombreUsuario(nombreUsuario)
        result.enqueue(object : Callback<UsuarioDataCollectionItem> {
            override fun onFailure(call: Call<UsuarioDataCollectionItem>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "$result", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<UsuarioDataCollectionItem>,
                response: Response<UsuarioDataCollectionItem>
            ) {
                if (response.code() == 404){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@LoginActivity,errorResponse.errorDetails,Toast.LENGTH_SHORT).show()
                }else if (response.code() == 500) {
                    val errorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(
                        this@LoginActivity,
                        errorResponse.errorDetails,
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    if(response.body()!!.contrasena == editTextContrasena.text.toString()){
                        UsuarioActual.usuarioLogueado = response.body()!!
                      val intent = Intent(this@LoginActivity,MainActivity::class.java)
                      startActivity(intent)
                      finish()
                    }else{
                        Toast.makeText(this@LoginActivity,"Contrasena incorrecta",Toast.LENGTH_SHORT).show()

                    }
                }

            }
        })

    }
}