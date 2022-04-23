package hn.edu.ujcv.pdm_2022_i_p3_equipo3.usuario

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.UsuarioDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.UsuarioService
import kotlinx.android.synthetic.main.activity_usuarios.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuariosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)

        btnHomeUsuarios.setOnClickListener {
            home()
        }

        btnNuevoUsuario.setOnClickListener {
            agregarUsuario()
        }

        callServiceGetAll()
    }

    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun agregarUsuario(){
        val intent = Intent(this, IngresarUsuarioActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val usuarioService : UsuarioService = RestEngine.buildService().create(UsuarioService::class.java)
        val result : Call<List<UsuarioDataCollectionItem>> = usuarioService.listUsuarios()
        result.enqueue(object : Callback<List<UsuarioDataCollectionItem>> {
            override fun onFailure(call: Call<List<UsuarioDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@UsuariosActivity, "Error${t.localizedMessage}", Toast.LENGTH_LONG).show()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<UsuarioDataCollectionItem>>,
                response: Response<List<UsuarioDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@UsuariosActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaUsuarios.adapter = listAdapter

            }
        })
    }
}