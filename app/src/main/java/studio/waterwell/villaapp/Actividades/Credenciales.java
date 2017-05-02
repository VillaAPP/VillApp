package studio.waterwell.villaapp.Actividades;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;

public class Credenciales extends AppCompatActivity {
    private WebView browser;

    private void iniciarParteWeb(){
        RespuestaWeb respuestaWeb = new RespuestaWeb(getApplicationContext());

        browser = (WebView) findViewById(R.id.browser);
        browser.loadUrl("http://villaapp.esy.es/login.html");
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(respuestaWeb, "ob");

        // Esto hace que se navegue por la web sin abrir navegador
        browser.setWebViewClient(new WebViewClient(){
            public boolean shouldOverriceUrlLoading(WebView view, String url){
                return false;
            }
        });

    }

    // TODO: Coger del bundle mandado por MainActivity el ArrayList de ubicaciones de toda la app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credenciales);
        iniciarParteWeb();
    }

    // TODO: Pasar esta clase a un archivo java de verdad
    class RespuestaWeb{
        private Context c;

        public RespuestaWeb(Context c){
            this.c = c;
        }

        @android.webkit.JavascriptInterface
        public void respuestaApp(String msj){
            Toast t = Toast.makeText(c, msj.toString(), Toast.LENGTH_SHORT);
            t.show();
        }

        // TODO: Guardar el ArrayList de ubicaciones de toda la app en el bundle
        @android.webkit.JavascriptInterface
        public void datosUsuario(String estado, String user, String pass){
            Usuario usuario = new Usuario(user, pass);

            Intent i = new Intent();
            i.setAction("android.intent.action.datosLogin");
            Bundle bundle = new Bundle();

            // Login
            if(estado.equals("1"))
                bundle.putBoolean("Registro", false);

            // Registro
            else
                bundle.putBoolean("Registro", true);

            bundle.putParcelable("Usuario", usuario);
            i.putExtra("Bundle", bundle);

            startActivity(i);

            // Finalizo esta actividad para sacarla de la pila
            finish();
        }
    }
}
