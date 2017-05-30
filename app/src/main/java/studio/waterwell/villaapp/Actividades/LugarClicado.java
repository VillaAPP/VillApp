package studio.waterwell.villaapp.Actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import studio.waterwell.villaapp.Controlador.ControladorOpiniones;
import studio.waterwell.villaapp.Fragmentos.FragMapa;
import studio.waterwell.villaapp.Fragmentos.fragOpinion;
import studio.waterwell.villaapp.Fragmentos.fragRincon;
import studio.waterwell.villaapp.Modelo.AdaptadorOpiniones;
import studio.waterwell.villaapp.Modelo.ILugar;
import studio.waterwell.villaapp.Modelo.IOpiniones;
import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.Modelo.Opinion;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;

public class LugarClicado extends AppCompatActivity implements IOpiniones, ILugar{

    /* Control de datos */

    private Lugar lugar;
    private Usuario usuario;
    // Controla si existe una opinion por parte del usuario
    private boolean existe;
    // Controla si al pusar el boton de atras sale del fragmento opinion o vuelve a la anterior actividad
    private boolean atras;

    /* Control de fragmentos */

    private FragmentManager fragmentManager;
    private fragRincon fragRincon;
    private fragOpinion fragOpinion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_clicado);

        cargarDatos();
    }

    @Override
    public void onBackPressed() {

        if(atras){
            cambiarFragmento(fragRincon,fragOpinion);
            atras = false;
        }
        else
            irAtras();
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
        bundle.putParcelable("lugar", lugar);
        bundle.putParcelable("usuario", usuario);
        i.putExtra("bundle", bundle);
        setResult(RESULT_OK, i);

        finish();
    }


    private void irAtras() {
        Intent i = getIntent();

        Bundle bundle = new Bundle();
        bundle.putInt("opcion", 0);
        bundle.putParcelable("lugar", lugar);
        bundle.putParcelable("usuario", usuario);
        i.putExtra("bundle", bundle);
        setResult(RESULT_OK, i);

        finish();
    }

    // TODO: Hay que acabar lo de llamar al fragmento
    @Override
    public void darOpinion(LatLng l1, LatLng l2) {
        Toast toast;

        if(existe){
            cambiarFragmento(fragOpinion, fragRincon);
            // Meter en fragOpinion todas las cosas que sean necesarias
            //toast = Toast.makeText(getApplicationContext(), "Esto no esta implementado aun.", Toast.LENGTH_SHORT);
            //toast.show();
            atras = true;
        }

        else{
            if(calcularDistancia(l1.latitude, l1.longitude, l2.latitude, l2.longitude)){
                cambiarFragmento(fragOpinion, fragRincon);
                // Meter en fragOpinion todas las cosas que sean necesarias
                //toast = Toast.makeText(getApplicationContext(), "Esto no esta implementado aun.", Toast.LENGTH_SHORT);
                //toast.show();
                atras = true;

            }
            else{
                toast = Toast.makeText(getApplicationContext(), "Debes estar en el lugar para poder dar una valoracion.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void cargarDatos(){
        Bundle bundle = getIntent().getBundleExtra("bundle");
        lugar = bundle.getParcelable("lugar");
        usuario = bundle.getParcelable("usuario");

        double lat =  bundle.getDouble("latitud");
        double lng =  bundle.getDouble("longitud");

        existe = false;
        atras = false;

        if(usuario.getOpiniones() != null){
            int index = 0;
            int numOpiniones = usuario.getOpiniones().size();
            boolean encontrado = false;

            while(!encontrado && index < numOpiniones){
                if(usuario.getOpiniones().get(index).getId().equals(lugar.getId())){
                    encontrado = true;
                    existe = true;
                }
                else
                    index++;
            }
        }

        fragmentManager = getSupportFragmentManager();
        fragRincon = fragRincon.newInstance(this.lugar, lat, lng, existe);
        fragOpinion = fragOpinion.newInstance(usuario, existe, this.lugar.getId());

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

    private boolean calcularDistancia(double lat1, double lng1, double lat2, double lng2) {
        boolean ok = false;

        double radioTierra = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        int distancia = (int) Math.floor(radioTierra * va2);

        if(distancia < 150)
            ok = true;

        return ok;
    }


    @Override
    public void cargarImagenLugar(Bitmap imagen_procesar) {
        fragRincon.cargarImagen(imagen_procesar);
    }
}
