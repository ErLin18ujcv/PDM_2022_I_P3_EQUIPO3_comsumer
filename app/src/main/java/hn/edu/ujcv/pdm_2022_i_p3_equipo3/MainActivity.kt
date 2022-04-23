package hn.edu.ujcv.pdm_2022_i_p3_equipo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.caso.CasoActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.centros.CentroActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.colonias.ColoniasActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.confirmados.ConfirmadosActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.documento.DocumentoActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.enfermedad.EnfermedadesActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.estado.EstadoActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.evaluacion.EvaluacionActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.laboratorio.LaboratorioActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.paciente.PacientesActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.rol.RolActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.sintomas.SintomasActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.usuario.UsuariosActivity
import hn.edu.ujcv.pdm_2022_i_p3_equipo3.vacuna.VacunasActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombreUsuarioTxt.text = "${UsuarioActual.usuarioLogueado!!.nombre} ${UsuarioActual.usuarioLogueado!!.apellido} "

        cardViewUsuarios.setOnClickListener {
            activityUsuarios()
        }

        cardViewPacientes.setOnClickListener {
            activityPacientes()
        }

        cardViewVacuna.setOnClickListener {
            activityVacunas()
        }

        cardViewSintomas.setOnClickListener {
            activitySintomas()
        }

        cardViewCentro.setOnClickListener {
            activityCentro()
        }

        cardViewDocumento.setOnClickListener {
            activityDocumento()
        }

        cardViewEvaluacion.setOnClickListener {
            activityEvaluacion()
        }

        cardViewEstado.setOnClickListener {
            activityEstado()
        }

        cardViewTipoCaso.setOnClickListener {
            activityCaso()
        }

        cardViewEnfermedadBase.setOnClickListener {
            activityEnfermedades()
        }
        cardViewRol.setOnClickListener {
            activityRol()
        }
        cardViewColonias.setOnClickListener {
            activityColonias()
        }
        cardViewConfirmados.setOnClickListener {
            activityConfirmados()
        }
        cardViewLaboratorios.setOnClickListener {
            activityLaboratorio()
        }

        btnLogout.setOnClickListener{
            logout()
        }


    }

    fun activityUsuarios(){
        val intent = Intent(this, UsuariosActivity::class.java)
        startActivity(intent)
    }

    fun activityPacientes(){
        val intent = Intent(this, PacientesActivity::class.java)
        startActivity(intent)
    }

    fun activityVacunas(){
        val intent = Intent(this, VacunasActivity::class.java)
        startActivity(intent)
    }

    fun activitySintomas(){
        val intent = Intent(this, SintomasActivity::class.java)
        startActivity(intent)
    }

    fun activityCentro(){
        val intent = Intent(this, CentroActivity::class.java)
        startActivity(intent)
    }

    fun activityDocumento(){
        val intent = Intent(this, DocumentoActivity::class.java)
        startActivity(intent)
    }

    fun activityEvaluacion(){
        val intent = Intent(this, EvaluacionActivity::class.java)
        startActivity(intent)
    }

    fun activityEstado(){
        val intent = Intent(this, EstadoActivity::class.java)
        startActivity(intent)
    }

    fun activityCaso(){
        val intent = Intent(this, CasoActivity::class.java)
        startActivity(intent)
    }

    fun activityEnfermedades(){
        val intent = Intent(this, EnfermedadesActivity::class.java)
        startActivity(intent)
    }

    fun activityRol(){
        val intent = Intent(this, RolActivity::class.java)
        startActivity(intent)
    }
    fun activityColonias() {
        val intent = Intent(this, ColoniasActivity::class.java)
        startActivity(intent)
    }

    fun activityLaboratorio() {
        val intent = Intent(this, LaboratorioActivity::class.java)
        startActivity(intent)
    }
    fun activityConfirmados() {
        val intent = Intent(this, ConfirmadosActivity::class.java)
        startActivity(intent)

    }
    fun logout(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}