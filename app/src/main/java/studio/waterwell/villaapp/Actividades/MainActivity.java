package studio.waterwell.villaapp.Actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import studio.waterwell.villaapp.Controlador.Controlador;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;

public class MainActivity extends AppCompatActivity {
    private Controlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlador = new Controlador(getApplicationContext());
    }

    // TODO: Falta por guardar en el bundle el ArrayList de todas las ubicaciones de la app
    @Override
    protected void onResume(){
        super.onResume();

        // Todo esto es una prueba para simular una carga de trabajo que tarda 3 segundos
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent();
                Bundle bundle = new Bundle();

                if(!controlador.existeUsuario())
                    i.setAction("android.intent.action.credenciales");

                else{
                    Usuario usuario = controlador.cargarUsuario();
                    bundle.putParcelable("Usuario", usuario);
                    i.setAction("android.intent.action.principal");
                    i.putExtra("Bundle", bundle);
                }

                startActivity(i);

                // Finalizo esta actividad para sacarla de la pila
                finish();
            }
        }, 3000);
    }

}