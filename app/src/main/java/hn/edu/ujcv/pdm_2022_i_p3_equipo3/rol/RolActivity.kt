package hn.edu.ujcv.pdm_2022_i_p3_equipo3.rol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RestApiError
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.entities.RolDataCollectionItem
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RestEngine
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.service.RolService
import kotlinx.android.synthetic.main.activity_rol.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RolActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rol)

        btnHomeLab.setOnClickListener {
            home()
        }
        btnNuevoLab.setOnClickListener{
            agregarRol() }

        callServiceGetAll()
    }
    fun home(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun agregarRol(){
        val intent = Intent(this, IngresarRolActivity::class.java)
        startActivity(intent)
    }

    private fun callServiceGetAll(){
        val rolService : RolService = RestEngine.buildService().create(RolService::class.java)
        val result : Call<List<RolDataCollectionItem>> = rolService.listRol()
        result.enqueue(object : Callback<List<RolDataCollectionItem>>{
            override fun onFailure(call: Call<List<RolDataCollectionItem>>, t: Throwable) {
                Toast.makeText(this@RolActivity, "Error$result",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<RolDataCollectionItem>>,
                response: Response<List<RolDataCollectionItem>>
            ) {
                val listAdapter = ArrayAdapter(this@RolActivity,android.R.layout.simple_list_item_1, response.body()!!)
                listaRoles.adapter = listAdapter

            }
        })
    }

//    private fun callServicePostRol(){
//        val rolService : RolService = RestEngine.buildService().create(RolService::class.java)
//        val result : Call<List<RolDataCollectionItem>> = rolService.listRol()
//        result.enqueue(object : Callback<List<RolDataCollectionItem>>{
//            override fun onFailure(call: Call<List<RolDataCollectionItem>>, t: Throwable) {
//                Toast.makeText(this@RolActivity,"Error",Toast.LENGTH_LONG).show()
//            }
//
//            override fun onResponse(
//                call: Call<List<RolDataCollectionItem>>,
//                response: Response<List<RolDataCollectionItem>>
//            ) {
//                val listAdapter = ArrayAdapter(this@RolActivity,android.R.layout.simple_list_item_1, response.body()!!)
//                listaRoles.adapter = listAdapter
//
//            }
//        })
//    }

    private fun addRol(rolData: RolDataCollectionItem, onResult : (RolDataCollectionItem?) -> Unit){
        val rolService : RolService = RestEngine.buildService().create(RolService::class.java)
        val result : Call<RolDataCollectionItem> = rolService.addRol(rolData)
        result.enqueue(object : Callback<RolDataCollectionItem>{
            override fun onFailure(call: Call<RolDataCollectionItem>, t: Throwable) {
                onResult(null)
            }

            override fun onResponse(
                call: Call<RolDataCollectionItem>,
                response: Response<RolDataCollectionItem>
            ) {
                if (response.isSuccessful){
                    val addedRol = response.body()!!
                    onResult(addedRol)
                }
                else if (response.code() == 500){
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), RestApiError::class.java)
                    Toast.makeText(this@RolActivity,errorResponse.errorDetails,Toast.LENGTH_SHORT).show()
                }else
                {
                    Toast.makeText(this@RolActivity,"Fallo al traer el crear y traer el item.",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun callServicePostRol(){
        val rol = RolDataCollectionItem(id = null,nombre = "Rolaaaaaa",descripcion = "Descripcion del rol")
        addRol(rol){
            if (it?.id != null){
                Toast.makeText(this@RolActivity,"OK"+it?.id,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@RolActivity,"Error"+it?.descripcion,Toast.LENGTH_SHORT).show()
            }
        }
    }

}