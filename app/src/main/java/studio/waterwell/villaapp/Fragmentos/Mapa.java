package studio.waterwell.villaapp.Fragmentos;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


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
    private boolean mover;

    // Latitud y longitud de mi posicion actual
    private Marker miMarca;
    private LatLng misCoordenadas;
    private LatLng ubicacion = new LatLng(0,0);
    private double lat = 0.0;
    private double lng = 0.0;

    public Mapa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mapa.
     */
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

        gMap.addMarker(new MarkerOptions()
                .position(aux)
                .title("Punto aux")
        );

        // Asigno que se pueda hacer zoom
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.setMinZoomPreference(14);
        gMap.getUiSettings().setMapToolbarEnabled(false);

        miUbicacion();
    }

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

    public void cambiarMapa(){

        if(gMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE)
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        else
            gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void volverUbicacion(){
        centrar = true;
    }

    /*
    * Estos dos metodos son pruebas para ver como reubicar la camara en mi ubicacion
    * y poner la camara del mapa en un punto
    */
    public LatLng getMisCoordenadas(){
        return misCoordenadas;
    }

    public void ubicarse(LatLng latLng){

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }

}