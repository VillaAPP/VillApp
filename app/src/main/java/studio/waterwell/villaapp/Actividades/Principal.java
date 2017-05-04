package studio.waterwell.villaapp.Actividades;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import studio.waterwell.villaapp.Mapa.Mapa;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    // Componentes de la vista
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Mapa mapa;
    private GoogleMap gMap;

    // Atributos necesarios
    private Usuario usuario;

    // Crea el fragmento del mapa. Falta por centrar el mapa en una ubicacion concreta y ponerle los puntos
    private void crearMapa() {
        mapa = Mapa.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmento, mapa)
                .commit();

        mapa.getMapAsync(this);
    }

    // TODO: Coger del bundle mandado por CargaDatosLogin el ArrayList de ubicaciones de toda la app
    private void cargarDatos() {
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        usuario = bundle.getParcelable("Usuario");

    }


    // Crea la barra lateral que se desplaza con el dedo para moverse en opciones
    private void cargarNavigationDrawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        cargarDatos();
        cargarNavigationDrawer();
        crearMapa();
    }

    // Dice que pasa al clicarse una opcion del menú de lateral (por defecto, se puede quitar)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Asigno que se pueda hacer zoom
        gMap.getUiSettings().setZoomControlsEnabled(true);

        // Permite ver tu ubicacion
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            gMap.getUiSettings().setMyLocationButtonEnabled(true);
            gMap.setMyLocationEnabled(true);
        }

        // Evita que el user al pinchar en las marcas salgan fuera de la app
        gMap.getUiSettings().setMapToolbarEnabled(false);

        // Establezco el zoom minimo a 16
        gMap.setMinZoomPreference(16);


        // Ubicacion de prueba añadida: Puerta del Sol
        LatLng cali = new LatLng(40.4169473, -3.7035284999999476);
        gMap.addMarker(new MarkerOptions()
                .position(cali)
                .title("Puerta del Sol")
        );

        // TODO: Obtener la ubicacion del user y  hacer como con el LatLng cali

        // Posicion de la camara de prueba: Puerta del sol
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cali)
                .zoom(16)
                .build();

        // Mueve la cámara al lugar establecido
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
