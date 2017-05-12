package studio.waterwell.villaapp.Actividades;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import studio.waterwell.villaapp.Controlador.ControladorOpiniones;
import studio.waterwell.villaapp.Fragmentos.FragMapa;
import studio.waterwell.villaapp.Fragmentos.fragOpinion;
import studio.waterwell.villaapp.Fragmentos.fragRincon;
import studio.waterwell.villaapp.Modelo.AdaptadorOpiniones;
import studio.waterwell.villaapp.Modelo.IOpiniones;
import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.Modelo.Opinion;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;

public class LugarClicado extends AppCompatActivity implements IOpiniones{

    private Lugar lugar;
    private Usuario usuario;
    private boolean existe;

    // Se encarga de cambiar los fragmentos de la vista
    private FragmentManager fragmentManager;
    private fragRincon fragRincon;
    private fragOpinion fragOpinion;

    // TODO: Habria que mandar un boolean a fragRincon viendo si el usuario tiene una opinion sobre el sitio o no
    private void cargarDatos(){
        Bundle bundle = getIntent().getBundleExtra("bundle");
        lugar = bundle.getParcelable("lugar");
        usuario = bundle.getParcelable("usuario");

        // TODO: Falta ver si el usuario tiene una opinion sobre el sitio

        fragmentManager = getSupportFragmentManager();
        fragRincon = fragRincon.newInstance(this.lugar);
        fragOpinion = fragOpinion.newInstance(usuario, existe);



        fragmentManager.beginTransaction()
                .add(R.id.fragmentoPpalLugar, fragRincon)
                .add(R.id.fragmentoPpalLugar, fragOpinion)
                .hide(fragOpinion)
                .commit();
    }

    private void cambiarFragmento(Fragment mostrar, Fragment quitar){
        fragmentManager.beginTransaction()
                .show(mostrar)
                .hide(quitar)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_clicado);

        cargarDatos();
    }

    @Override
    public void cargarOpiniones(ArrayList<Opinion> opiniones) {
        fragRincon.cargarOpiniones(opiniones);
    }

    @Override
    public void cargarRuta() {
        Intent i = getIntent();

        Bundle bundle = new Bundle();
        bundle.putInt("opcion", 2);
        bundle.putDouble("latitud", lugar.getLatitud());
        bundle.putDouble("longitud", lugar.getLongitud());
        i.putExtra("bundle", bundle);

        setResult(RESULT_OK, i);

        finish();
    }

    @Override
    public void irAtras() {
        Intent i = getIntent();

        Bundle bundle = new Bundle();
        bundle.putInt("opcion", 0);
        bundle.putParcelable("lugar", lugar);
        i.putExtra("bundle", bundle);

        setResult(RESULT_OK, i);

        finish();
    }

    @Override
    public void darOpinion() {
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Esto no esta implementado aun.", Toast.LENGTH_SHORT);
        toast1.show();

        // TODO: Ver si estoy a una distancia optima y de ser asi cambiar de fragmento
        // cambiarFragmento(fragRincon, fragOpinion);
    }



}
