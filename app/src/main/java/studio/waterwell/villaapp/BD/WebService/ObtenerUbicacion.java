package studio.waterwell.villaapp.BD.WebService;

import android.content.Context;
import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Efecto Dopler on 07/05/2017.
 */

public class ObtenerUbicacion extends AsyncTask<LatLng, Void, String>{
    private Context c;

    public ObtenerUbicacion(Context c){
        this.c = c;
    }
    @Override
    protected String doInBackground(LatLng... params) {
        String direccion = "";
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + params[0].latitude + "," + params[0].longitude + "&AIzaSyA0ZT_DPBr2ZkxukV2WZDAOpUwBx-sZwnQ");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Habilito el poder recibir JSON a la conexión
            connection.setDoOutput(true);

            // No habilito el uso de caches
            connection.setUseCaches(false);

            // Especifico que mando JSON y algunas propiedades necesarias
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"
                    + " (Linux, Android 1.5; es-Es) Ejemplo HTTP");

            // Realizo la conexión
            connection.connect();

            int respuesta = connection.getResponseCode();

            if (respuesta == HttpURLConnection.HTTP_OK) {
                // Declaro la lectura
                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                // Creo un buffer de lectura
                BufferedReader read = new BufferedReader(in);

                // String donde guardar los resultados
                StringBuilder result = new StringBuilder();

                // String donde ir guardando linea a linea
                String linea = read.readLine();

                while (linea != null) {
                    result.append(linea);
                    linea = read.readLine();
                }

                // Creo un JSON donde almaceno lo guardado en el while
                JSONObject respuestaJSON = new JSONObject(result.toString());

                // Guardo en la variable la respuesta "estado" de la consulta
                String estado = respuestaJSON.getString("status");

                if(estado.equals("OK")){
                    JSONArray resultados = respuestaJSON.getJSONArray("results");
                    JSONObject direccionJSON = resultados.getJSONObject(1);
                    direccion = direccionJSON.getString("formatted_address");
                }
            }

            connection.disconnect();

        } catch (MalformedURLException e) {
            Log.i("Error", "Fallo en la URL");
        } catch (IOException e) {
            Log.i("Error", "Fallo IOException");
        } catch (JSONException e) {
            Log.i("Error", "Fallo de JSON");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return direccion;
    }

    @Override
    protected void onPostExecute(String s) {
        // Cambiar esto por llamar a una interfaz que lleve a Principal
        Toast toast1 = Toast.makeText(c, s, Toast.LENGTH_SHORT);
        toast1.show();
    }
}
