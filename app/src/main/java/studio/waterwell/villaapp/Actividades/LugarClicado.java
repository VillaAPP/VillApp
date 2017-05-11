package studio.waterwell.villaapp.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;

public class LugarClicado extends AppCompatActivity {
    private final int LUGAR = 1;

    private Lugar lugar;
    private Usuario usuario;

    private Button atras;
    private Button opinar;
    private Button ruta;
    private TextView nombre;
    private TextView direccion;
    private TextView descripcion;


    private void cargarDatos(){
        Bundle bundle = getIntent().getBundleExtra("bundle");
        lugar = bundle.getParcelable("lugar");
        usuario = bundle.getParcelable("usuario");
    }

    private void cargarVista(){
        atras = (Button) findViewById(R.id.lugar_atras);
        opinar = (Button) findViewById(R.id.lugar_opinion);
        ruta = (Button) findViewById(R.id.lugar_ruta);

        nombre = (TextView) findViewById(R.id.lugar_nombre);
        direccion = (TextView) findViewById(R.id.lugar_direccion);
        descripcion = (TextView) findViewById(R.id.lugar_descripcion);

        nombre.setText(lugar.getNombre());
        direccion.setText(lugar.getDireccion());
        descripcion.setText(lugar.getDescripcion());


        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();

                Bundle bundle = new Bundle();
                bundle.putInt("opcion", 0);
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
}
