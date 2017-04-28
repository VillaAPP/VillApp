package studio.waterwell.villaapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView browser;

    // Inicia el WebView con los datos oportunos
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarParteWeb();
    }

    // Esta clase hay que crearla de verdad en un archivo java, así solo es de prueba
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

        @android.webkit.JavascriptInterface
        public void datosUsuario(String user, String pass){
            // TODO: Generar un objeto usuario con estos datos, guardarlo en la bd interna de la app
            // TODO: y mandarlo por bundle a la actividad principal
        }
    }
}
