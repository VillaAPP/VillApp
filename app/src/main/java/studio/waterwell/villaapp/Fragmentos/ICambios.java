package studio.waterwell.villaapp.Fragmentos;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Efecto Dopler on 07/05/2017.
 */

public interface ICambios {

    // Se llama en FragMapa para pasar del mapa a Principal las coordenadas de mi ubicacion actual
    public void obtenerUbicacion(LatLng latLng);

    // Manda las coordenadas desde el fragmento MisRonces a principal para trazar una ruta
    public void mandarCoordenadas(LatLng latLng);

    // Manda desde el AsynTack ObtenerRuta el conjunto de puntos que dibujan en el mapa la ruta desde mi ubicacion hasta el destino
    public void obtenerRuta(List<List<HashMap<String, String>>> ruta);
}
