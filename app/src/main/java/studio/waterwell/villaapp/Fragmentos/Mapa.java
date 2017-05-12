package studio.waterwell.villaapp.Fragmentos;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import studio.waterwell.villaapp.Modelo.ICambios;
import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.Modelo.Usuario;



public class Mapa extends SupportMapFragment implements OnMapReadyCallback{

    private final int LUGAR = 1;

    private final int MODIFICADO = 1;
    private final int RUTA = 2;

    /* array de lugares */

    private ArrayList<Lugar> lugares;
    private Usuario usuario;
    private Marker marcado;
    private ICambios cambios;
    private GoogleMap gMap;
    private Location location;
    private LocationManager locationManager;
    private double lat;
    private double lng;

    /* posicion actual */

    private LatLng misCoordenadas;

    /* Ruta que une dos puntos del mapa */

    private List<List<HashMap<String, String>>> ruta;
    private PolylineOptions lineOptions;
    private Polyline polyFinal;
    private boolean enRuta;


    public Mapa() {
        // Required empty public constructor
    }

    public static Mapa newInstance(ArrayList<Lugar> lugares, Usuario usuario) {
        Mapa fragment = new Mapa();
        Bundle args = new Bundle();
        args.putParcelableArrayList("lugares", lugares);
        args.putParcelable("usuario", usuario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            lugares = getArguments().getParcelableArrayList("lugares");
            usuario = getArguments().getParcelable("usuario");
        }

        location = null;
        lineOptions = null;
        enRuta = false;
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

        for(int i = 0; i < lugares.size(); i++) {

            Marker marker = gMap.addMarker(new MarkerOptions()
                    .position(lugares.get(i).obtenerCoordenadas())
                    .title(lugares.get(i).getNombre().toString())
            );
            marker.setTag(lugares.get(i));
        }

        // Asigno que se pueda hacer zoom
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.setMinZoomPreference(10);
        gMap.getUiSettings().setMapToolbarEnabled(false);

        // Si no hay permisos de gps los pido
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.setMyLocationEnabled(true);

        miUbicacion();

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(misCoordenadas, 16));

        //TODO: Fijar el evento de que cuando clico en un marcador aparece la info del lugar
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marcado = marker;

                Lugar lugarAuxiliar = (Lugar) marcado.getTag();

                Intent i = new Intent();
                i.setAction("android.intent.action.lugar");

                Bundle bundle = new Bundle();
                bundle.putParcelable("usuario", usuario);
                bundle.putParcelable("lugar", lugarAuxiliar);
                i.putExtra("bundle", bundle);
                startActivityForResult(i, LUGAR);

                return true;
            }
        });
    }

    // Actualiza longitud y latitud de la ubicacion del usuario
    private void actualizarUbicacion(Location location) {

        if (location != null) {
            if(lat != location.getLatitude() && lng != location.getLongitude()){
                misCoordenadas = new LatLng(location.getLatitude(), location.getLongitude());
                lat = location.getLatitude();
                lng = location.getLongitude();
                if(enRuta)
                    cambios.modificarRuta(misCoordenadas);
            }

        }

        // Si no hay una localizacion previa carga la puerta del Sol
        // TODO: Ver si esto funciona
        else{
            lat = 40.4166635;
            lng = -3.7041686999999683;
            misCoordenadas = new LatLng(lat, lng);
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

        // Si no hay permisos de gps los pido
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        actualizarUbicacion(location);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locListener);
    }

    // Cambia entre mapa satelite y normal
    public void cambiarMapa(){

        if(gMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE)
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        else
            gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
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

        enRuta = true;

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
        enRuta = false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cambios = (ICambios) getActivity();
    }


    // Recoge los datos de la actividad Lugar
    // TODO: Falta ver cuando se ha hecho una opinion
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getBundleExtra("bundle");

        if(resultCode == getActivity().RESULT_OK){

            Lugar  modificado = bundle.getParcelable("lugar");
            marcado.setTag(modificado);
            int opcion = bundle.getInt("opcion");

            // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
           if(opcion == RUTA){
               double lat = bundle.getDouble("latitud");
               double lng = bundle.getDouble("longitud");
               cambios.mandarCoordenadas(new LatLng(lat,lng));
            }
        }

    }
}