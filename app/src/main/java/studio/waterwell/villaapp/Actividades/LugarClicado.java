package studio.waterwell.villaapp.Actividades;

import android.content.Intent;
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
import studio.waterwell.villaapp.Modelo.AdaptadorOpiniones;
import studio.waterwell.villaapp.Modelo.IOpiniones;
import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.Modelo.Opinion;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;

public class LugarClicado extends AppCompatActivity implements IOpiniones{

    private Lugar lugar;
    private Usuario usuario;
    private ControladorOpiniones controladorOpiniones;

    private Button atras;
    private Button opinar;
    private Button ruta;
    private TextView nombre;
    private TextView direccion;
    private TextView descripcion;
    private ListView lista;
    private AdaptadorOpiniones adaptador;

    private void cargarDatos(){
        Bundle bundle = getIntent().getBundleExtra("bundle");
        lugar = bundle.getParcelable("lugar");
        usuario = bundle.getParcelable("usuario");
        ArrayList<Opinion> opiniones = new ArrayList<Opinion>();

        if(lugar.getNumOpiniones() != 0)
            opiniones = lugar.getOpiniones();

        adaptador = new AdaptadorOpiniones(this, opiniones);

        controladorOpiniones = new ControladorOpiniones(this, this, lugar.getId());
    }

    private void cargarVista(){
        atras = (Button) findViewById(R.id.lugar_atras);
        opinar = (Button) findViewById(R.id.lugar_opinion);
        ruta = (Button) findViewById(R.id.lugar_ruta);

        nombre = (TextView) findViewById(R.id.lugar_nombre);
        direccion = (TextView) findViewById(R.id.lugar_direccion);
        descripcion = (TextView) findViewById(R.id.lugar_descripcion);
        lista = (ListView) findViewById(R.id.lista_opiniones);

        nombre.setText(lugar.getNombre());
        direccion.setText(lugar.getDireccion());
        descripcion.setText(lugar.getDescripcion());
        lista.setAdapter(adaptador);

        // Solo si no hay opiniones del sitio miramos en la BD externa
        if(lugar.getNumOpiniones() == 0)
            controladorOpiniones.obtenerOpiniones();

        // Si hay opiniones no carga del controlador nada
        else{
            RelativeLayout r1 = (RelativeLayout) findViewById(R.id.layout_carga);
            RelativeLayout r2 = (RelativeLayout) findViewById(R.id.layout_lista);
            r1.setVisibility(View.INVISIBLE);
            r2.setVisibility(View.VISIBLE);
        }

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();

                Bundle bundle = new Bundle();
                bundle.putInt("opcion", 0);
                bundle.putParcelable("lugar", lugar);
                i.putExtra("bundle", bundle);

                setResult(RESULT_OK, i);

                finish();
            }
        });

        ruta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = getIntent();

                        Bundle bundle = new Bundle();
                        bundle.putInt("opcion", 2);
                        bundle.putDouble("latitud", lugar.getLatitud());
                        bundle.putDouble("longitud", lugar.getLongitud());
                        i.putExtra("bundle", bundle);

                        setResult(RESULT_OK, i);

                        finish();
                    }
                });

        opinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Esto no esta implementado aun.", Toast.LENGTH_SHORT);

                toast1.show();
            }
        });
    }

    // TODO: Falta cargar la foto y las opiniones del lugar. Tambien hay que hacer el formulario de mandar opinion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_clicado);

        cargarDatos();
        cargarVista();

    }

    // Actualiza las opiniones del lugar
    @Override
    public void cargarOpiniones(ArrayList<Opinion> opiniones) {
        lugar.setOpiniones(opiniones);
        adaptador.setLista(opiniones);
        adaptador.notifyDataSetChanged();
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.layout_carga);
        RelativeLayout r2 = (RelativeLayout) findViewById(R.id.layout_lista);
        r1.setVisibility(View.INVISIBLE);
        r2.setVisibility(View.VISIBLE);
    }

}
