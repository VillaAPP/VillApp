package studio.waterwell.villaapp.Modelo;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Efecto Dopler on 11/05/2017.
 */

public interface IOpiniones {

    // Devuelve a fragRincon las opiniones cargadas por el AsynkTask CargarOpinion
    public void cargarOpiniones(ArrayList<Opinion> opiniones);

    // Al clicar en in atras en el fragmento de rincon se vuelve a la actividad del mapa con las coordenadas
    public void cargarRuta();

    public void darOpinion(LatLng l1, LatLng l2);
}
