package studio.waterwell.villaapp.BD.WebService;

import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import studio.waterwell.villaapp.Modelo.IMisOpiniones;
import studio.waterwell.villaapp.Modelo.IOpiniones;
import studio.waterwell.villaapp.Modelo.MiOpinion;
import studio.waterwell.villaapp.Modelo.Opinion;
import studio.waterwell.villaapp.Modelo.Usuario;

/**
 * Created by Efecto Dopler on 15/05/2017.
 */

public class CargarOpinionesUsuario extends AsyncTask<String , Void, ArrayList<MiOpinion>> {

    private IMisOpiniones iMisOpiniones;

    public CargarOpinionesUsuario(IMisOpiniones iMisOpiniones) {
        this.iMisOpiniones = iMisOpiniones;
    }

    protected ArrayList<MiOpinion> doInBackground(String... params) {
        ArrayList<MiOpinion> lista = new ArrayList<MiOpinion>();

        try {
            URL url = new URL("http://www.villaApp.esy.es/get_opiniones_usuario.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Habilito el poder mandar JSON a la conexión
            connection.setDoInput(true);

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

            JSONObject paramsJSON = new JSONObject();
            paramsJSON.put("userName", params[0]);


            // Envio el json mediante el método POST
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(paramsJSON.toString());
            writer.flush();
            writer.close();


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
                String estadoJSON = respuestaJSON.getString("estado");

                if (estadoJSON.equals("1")) {
                    String auxJSON = respuestaJSON.getString("Opiniones");

                    if (!auxJSON.equals("[]")) {
                        JSONArray opinionesJSON = respuestaJSON.getJSONArray("Opiniones");

                        for (int i = 0; i < opinionesJSON.length(); i++) {
                            JSONObject opinionJSON = opinionesJSON.getJSONObject(i);
                            MiOpinion opinion = new MiOpinion(opinionJSON.getString("idLugar"), opinionJSON.getInt("rate"), opinionJSON.getString("opinion"));
                            lista.add(opinion);
                        }
                    }
                }

                connection.disconnect();
            }

        } catch (MalformedURLException e) {
            Log.i("Error", "Fallo en la URL");
        } catch (IOException e) {
            Log.i("Error", "Fallo IOException");
        } catch (JSONException e) {
            Log.i("Error", "Fallo de JSON");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    protected void onPostExecute(ArrayList<MiOpinion> resultado) {
        iMisOpiniones.cargarOpiniones(resultado);
    }
}
