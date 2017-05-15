package studio.waterwell.villaapp.Actividades;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import studio.waterwell.villaapp.Controlador.ControladorOpiniones;
import studio.waterwell.villaapp.Modelo.IMisOpiniones;
import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.Modelo.MiOpinion;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.Controlador.Controlador;
import studio.waterwell.villaapp.R;

public class CargaDatosLogin extends AppCompatActivity implements IMisOpiniones{
    private Usuario usuario;
    private ArrayList<Lugar> lugares;
    private Controlador controlador;
    private ControladorOpiniones controladorOpiniones;

    private void cargarDatos(){
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        usuario = bundle.getParcelable("Usuario");
        lugares = bundle.getParcelableArrayList("lugares");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_datos_login);
        cargarDatos();
        controlador = new Controlador(getApplicationContext());
        controladorOpiniones = new ControladorOpiniones(getApplicationContext(), this);
    }


    @Override
    protected void onResume(){
        super.onResume();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                controlador.guardarUsuarioBDInterna(usuario);
                controladorOpiniones.obtenerOpinionesUsuarioExterna(usuario);

            }
        }, 1000);
    }

    @Override
    public void cargarOpiniones(ArrayList<MiOpinion> opiniones) {
        controladorOpiniones.guardarOpinionesInterna(opiniones);
        usuario.addOpiniones(opiniones);
        Bundle bundle = new Bundle();
        Intent i = new Intent();
        i.setAction("android.intent.action.principal");
        bundle.putParcelableArrayList("lugares", lugares);
        bundle.putParcelable("Usuario", usuario);
        i.putExtra("Bundle", bundle);
        startActivity(i);
        finish();
    }
}
