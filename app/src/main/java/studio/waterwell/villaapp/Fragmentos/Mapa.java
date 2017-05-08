package studio.waterwell.villaapp.Fragmentos;



import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Mapa extends SupportMapFragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private final static int EJEMPLO = 1;

    private GoogleMap gMap;
    private Marker[] marcas;
    private Location location;
    private LocationManager locationManager;

    private boolean centrar;

    /* Latitud y longitud de mi posicion actual */

    private Marker miMarca;
    private LatLng misCoordenadas;
    private double lat = 0.0;
    private double lng = 0.0;

    /* Ruta que une dos puntos del mapa */

    private List<List<HashMap<String, String>>> ruta;
    private PolylineOptions lineOptions;
    private Polyline polyFinal;

    public Mapa() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Mapa newInstance(String param1, String param2) {
        Mapa fragment = new Mapa();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

    // Prueba
    public static Mapa newInstance() {
        Mapa fragment = new Mapa();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           //
        }
        lineOptions = null;
        centrar = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = super.onCreateView(inflater, container, savedInstanceState);
        getMapAsync(this);
        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        LatLng aux = new LatLng(40.39991817, -3.6941729);
        LatLng aux2 = new LatLng(40.4166635, -3.7041687);
        LatLng aux3 = new LatLng(40.4072103, -3.6945893);

        gMap.addMarker(new MarkerOptions()
                .position(aux)
                .title("Calle Delicias")
        );
        gMap.addMarker(new MarkerOptions()
                .position(aux2)
                .title("Plaza del Sol")
        );
        gMap.addMarker(new MarkerOptions()
                .position(aux3)
                .title("Plaza de Atocha")
        );

        // Asigno que se pueda hacer zoom
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.setMinZoomPreference(14);
        gMap.getUiSettings().setMapToolbarEnabled(false);

        miUbicacion();
    }

    // Coloca el icono en el mapa donde esta el usuario
    private void agregarMiMarcador(double lat, double lng) {
        misCoordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(misCoordenadas, 16);

        if (miMarca != null)
            miMarca.remove();

        miMarca = gMap.addMarker(new MarkerOptions()
                .position(misCoordenadas)
                .title("Ubicación actual")
        );

        // Una vez centrada la camara en nuestra ubicación se desactiva la opcion hasta que el desea volver a centrar
        if(centrar){
            gMap.animateCamera(miUbicacion);
            centrar = false;
        }

    }

    // Actualiza longitud y latitud de la ubicacion del usuario
    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMiMarcador(lat, lng);
        }
    }

    // Listener de ubicacion que actualiza la posicion
    private LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    // Obtiene las coordenadas via GPS y actualiza la posicion cada 8 segundos
    private void miUbicacion() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        actualizarUbicacion(location);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);
    }

    // Cambia entre mapa satelite y normal
    public void cambiarMapa(){

        if(gMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE)
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        else
            gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    // Centra la camara en el usuario
    public void volverUbicacion(){
        centrar = true;
    }

   // Devuelve las coordenadas del usuario
    public LatLng getMisCoordenadas(){
        return misCoordenadas;
    }

    // Ubica la camara en una posicion concreta
    public void ubicarse(LatLng latLng){
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }

    // Fija la ruta
    public void setRuta(List<List<HashMap<String, String>>> ruta) {
        this.ruta = ruta;
        dibujaRuta(this.ruta);
    }

    // Dibuja una ruta en el mapa
    private void dibujaRuta(List<List<HashMap<String, String>>> ruta) {
        ArrayList<LatLng> puntos  = new ArrayList<LatLng>();

        if(lineOptions != null)
            borraRuta();


        // Recorro toda la ruta otbtenida
        for(int i=0;i<ruta.size();i++){
            puntos = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Consigo el path de la ruta
            List<HashMap<String, String>> path = ruta.get(i);

            // Consigo todos los puntos de la ruta a trazar
            for(int j=0;j<path.size();j++){
                HashMap<String,String> punto = path.get(j);

                double lat = Double.parseDouble(punto.get("lat"));
                double lng = Double.parseDouble(punto.get("lng"));

                LatLng posicion = new LatLng(lat, lng);

                // Por ultimo, añado todos los datos al array
                puntos.add(posicion);
            }

            // Termino de añadir todos los puntos y el color de la ruta en el objeto lineOptions
            lineOptions.addAll(puntos);
            lineOptions.width(4);
            lineOptions.color(Color.GREEN);
        }

        // Dibujo en mi objeto googleMap la ruta y guardo en polyFinal el resultado por si quiero borrar la ruta
        polyFinal = gMap.addPolyline(lineOptions);
    }

    // Borra la ruta del mapa
    public void borraRuta(){
        polyFinal.remove();
        lineOptions = null;
    }
}