package studio.waterwell.villaapp.Actividades;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.Controlador.Controlador;
import studio.waterwell.villaapp.R;

public class CargaDatosLogin extends AppCompatActivity {
    private Usuario usuario;
    private ArrayList<Lugar> lugares;
    private Controlador controlador;
    // Si no es registro mirará si hay ubicaciones guardadas en la bd interna/externa y las cargara
    private Boolean registro;

    // TODO: Recoger el ArrayList de todas las ubicaciones mandado por Credenciales
    private void cargarDatos(){
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        usuario = bundle.getParcelable("Usuario");
        registro = bundle.getBoolean("Registro");
        lugares = bundle.getParcelableArrayList("lugares");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_datos_login);
        cargarDatos();
        controlador = new Controlador(getApplicationContext());
    }


    @Override
    protected void onResume(){
        super.onResume();

        // Todo esto es una prueba para simular una carga de trabajo que tarda 3 SEGUNDOS
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                controlador.guardarUsuarioBDInterna(usuario);

                Intent i = new Intent();
                i.setAction("android.intent.action.principal");

                Bundle bundle = new Bundle();
                if(!registro){
                    // TODO: Si no es registro se cargan ubicaciones de bd interna/externa.
                }

                bundle.putParcelableArrayList("lugares", lugares);
                bundle.putParcelable("Usuario", usuario);
                i.putExtra("Bundle", bundle);

                startActivity(i);

                finish();
            }
        }, 3000);
    }
}
