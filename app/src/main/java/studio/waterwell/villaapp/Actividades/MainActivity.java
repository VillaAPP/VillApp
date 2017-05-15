package studio.waterwell.villaapp.Actividades;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import studio.waterwell.villaapp.Controlador.Controlador;
import studio.waterwell.villaapp.Controlador.ControladorLugar;
import studio.waterwell.villaapp.Controlador.ControladorOpiniones;
import studio.waterwell.villaapp.Modelo.IMisOpiniones;
import studio.waterwell.villaapp.Modelo.MiOpinion;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;

public class MainActivity extends AppCompatActivity implements IMisOpiniones{
    private Controlador controlador;
    private ControladorLugar controladorLugar;
    private ControladorOpiniones controladorOpiniones;
    private  Usuario usuario;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlador = new Controlador(getApplicationContext());
        controladorOpiniones = new ControladorOpiniones(getApplicationContext(), this);
        bundle = new Bundle();
    }

    @Override
    protected void onResume(){
        super.onResume();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                controladorLugar = new ControladorLugar(getApplicationContext());
                Intent i = new Intent();

                //Obtenemos la lista de lugares desde el controlador.
                bundle.putParcelableArrayList("lugares", controladorLugar.getLugares());

                if(!controlador.existeUsuario())
                    mandarIntent(i, "android.intent.action.credenciales");


                else{
                    usuario = controlador.cargarUsuario();

                    if(controladorOpiniones.existenOpinionesInternas()){
                        controladorOpiniones.obtenerOpinionesUsuarioInterna(usuario);
                        bundle.putParcelable("Usuario", usuario);
                        mandarIntent(i, "android.intent.action.principal");
                    }

                    else
                        controladorOpiniones.obtenerOpinionesUsuarioExterna(usuario);

                }
            }
        }, 1000);
    }

    @Override
    public void cargarOpiniones(ArrayList<MiOpinion> opiniones) {
        controladorOpiniones.guardarOpinionesInterna(opiniones);
        usuario.addOpiniones(opiniones);
        bundle.putParcelable("Usuario", usuario);
        Intent i = new Intent();
        mandarIntent(i, "android.intent.action.principal");
    }

    private void mandarIntent(Intent i, String accion){
        i.setAction(accion);
        i.putExtra("Bundle", bundle);
        startActivity(i);
        finish();
    }

}
