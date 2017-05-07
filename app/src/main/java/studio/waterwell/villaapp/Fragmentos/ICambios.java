package studio.waterwell.villaapp.Fragmentos;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Efecto Dopler on 07/05/2017.
 */

public interface ICambios {
    // Se llama en FragMapa para pasar del mapa a Principal las coordenadas de mi ubicacion actual
    public void obtenerUbicacion(LatLng latLng);

    public void mandarCoordenadas(LatLng latLng);
}
