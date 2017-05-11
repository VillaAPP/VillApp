package studio.waterwell.villaapp.BD.WebService;

import android.os.AsyncTask;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import studio.waterwell.villaapp.Modelo.ICambios;

/**
 * Created by Efecto Dopler on 08/05/2017.
 */

public class ObtenerRuta extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

    private ICambios cambios;

    public ObtenerRuta(ICambios cambios){
        this.cambios = cambios;
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... params) {
        return getRuta(params[0], params[1]);
    }

    protected void onPostExecute(List<List<HashMap<String, String>>> ruta) {

        // Si ha salido bien, haremos un callBack a Principal con la ruta para actualizar el mapa
       if(ruta != null)
        cambios.obtenerRuta(ruta);
    }

    private List<List<HashMap<String, String>>> getRuta(String origen, String destino) {

        // Defino la lista que voy a devolver
        List<List<HashMap<String, String>>> puntos = new ArrayList<List<HashMap<String,String>>>();

        try {

            URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin="
                    +origen+"&destination="+destino+"&mode=walking&key=AIzaSyA0ZT_DPBr2ZkxukV2WZDAOpUwBx-sZwnQ");

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

                if(estado.equals("OK"))
                    puntos = manipularResultados(respuestaJSON);

            }

            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return puntos;
    }


    // Manipulo la ingente cantidad de datos obtenidos de Google para obtener puntos
    public List<List<HashMap<String,String>>> manipularResultados(JSONObject jObject){

        //Lista a devolver y JSON necesarios para procesar todos los datos
        List<List<HashMap<String, String>>> ruta = new ArrayList<List<HashMap<String,String>>>();
        JSONArray rutasJSON, legsJSON, stepsJSON;

        try {

            rutasJSON = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for(int i= 0; i < rutasJSON.length(); i++){

                legsJSON = ( (JSONObject)rutasJSON.get(i)).getJSONArray("legs");
                List<HashMap<String, String>> puntos = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for(int j = 0; j < legsJSON.length(); j++){
                    stepsJSON = ( (JSONObject)legsJSON.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for(int k = 0; k < stepsJSON.length(); k++){
                        String coordendas = "";
                        coordendas = (String)((JSONObject)((JSONObject)stepsJSON.get(k)).get("polyline")).get("points");
                        List<LatLng> list = obtenerCoordenadas(coordendas);

                        /** Traversing all points */
                        for(int l = 0; l < list.size(); l++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                            puntos.add(hm);
                        }
                    }
                    ruta.add(puntos);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
        }

        return ruta;
    }

    // Metodo auxiliar que termina de obtener datos esenciales para los puntos
    private List<LatLng> obtenerCoordenadas(String coordenadas) {

        // Lista que vamos a devolver y coordenadas a calcular
        List<LatLng> listaCoordenadas = new ArrayList<LatLng>();
        int indice = 0, tam = coordenadas.length();
        int latitud = 0, longitud = 0;

        while (indice < tam) {
            int b, shift = 0, resultado = 0;

            do {
                b = coordenadas.charAt(indice++) - 63;
                resultado |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((resultado & 1) != 0 ? ~(resultado >> 1) : (resultado >> 1));
            latitud = latitud + dlat;

            shift = 0;
            resultado = 0;

            do {
                b = coordenadas.charAt(indice++) - 63;
                resultado |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlng = ((resultado & 1) != 0 ? ~(resultado >> 1) : (resultado >> 1));
            longitud = longitud + dlng;
            LatLng p = new LatLng((((double) latitud / 1E5)), (((double) longitud / 1E5)));
            listaCoordenadas.add(p);
        }

        return listaCoordenadas;
    }

}
