package studio.waterwell.villaapp.Actividades;



import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import studio.waterwell.villaapp.Fragmentos.FragMapa;
import studio.waterwell.villaapp.Fragmentos.FragMisRincones;
import studio.waterwell.villaapp.Fragmentos.FragRincones;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    // Componentes de la vista
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    // Componentes de gragmentos
    private FragmentManager fragmentManager;  // Se encarga de cambiar los fragmentos de la vista
    private Fragment[] fragmentos;            // Almacena los distintos fragmentos de la vista
    private Fragment fragmentoActual;         // Fragmento que esta siendo usado por la actividad

    // Encargados de acceder a una posicion de Fragment[] fragmentos
    final private  int MAPA = 0;
    final private  int LISTA_RINCONES = 1;
    final private  int RINCONES_VISITADOS = 2;
    private int nFragActual;

    // Atributos necesarios
    private Usuario usuario;

    // TODO: Coger del bundle mandado por CargaDatosLogin el ArrayList de ubicaciones de toda la app
    private void cargarDatos() {
        Bundle bundle = getIntent().getBundleExtra("Bundle");
        usuario = bundle.getParcelable("Usuario");

    }


    // Cambia la cabecera de la barra por los valores del usuario logeado
    private void actualizarCabeceraUser(){
        // Obtengo la vista de la cabecera que forma el navigationView
        View auxview = navigationView.getHeaderView(0);

        // Cargo los campos user y email que forma la cabecera y les cambio el valor
        TextView user = (TextView) auxview.findViewById(R.id.nav_usuario);
        user.setText(usuario.getUserName());
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
        actualizarCabeceraUser();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void cargarFragmentos(){
        fragmentManager = getSupportFragmentManager();
        fragmentos = new Fragment[3];
        fragmentos[MAPA] = FragMapa.newInstance();
        fragmentos[LISTA_RINCONES] = FragRincones.newInstance();
        fragmentos[RINCONES_VISITADOS] = FragMisRincones.newInstance();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        cargarDatos();
        cargarNavigationDrawer();

        cargarFragmentos();

        // Cargo como fragmento inicial el mapa de la villa
        fragmentoActual = fragmentos[MAPA];
        nFragActual = MAPA;
        fragmentManager.beginTransaction()
                .add(R.id.fragmentoPpal, fragmentoActual)
                .commit();
        getSupportActionBar().setTitle("Mapa de la Villa");
    }

    // Dice que pasa al clicarse una opcion del menú de lateral (por defecto, se puede quitar)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Guardo el fragmento que estaba usando
        guardarFragmentoModificado();

        if (id == R.id.nav_mapa) {
            fragmentoActual = fragmentos[MAPA];
            nFragActual = MAPA;
            cambiarFragmento(fragmentoActual, "Mapa de la Villa");
        }
        else if (id == R.id.nav_lista_lugares) {
            fragmentoActual = fragmentos[LISTA_RINCONES];
            nFragActual = LISTA_RINCONES;
            cambiarFragmento(fragmentoActual, "Rincones de la Villa");
        }
        else if (id == R.id.nav_lista_visitados) {
            fragmentoActual = fragmentos[RINCONES_VISITADOS];
            nFragActual = RINCONES_VISITADOS;
            cambiarFragmento(fragmentoActual, "Rincones visitados");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Se encarga de cambiar los fragmentos de la actividad
    private void cambiarFragmento(Fragment fragmento, String nombre){
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentoPpal, fragmento)
                .commit();
        getSupportActionBar().setTitle(nombre);
    }

    // Al cambiar de fragmento, me encargo de guardar el que ya he usado
    private void guardarFragmentoModificado(){
        fragmentos[nFragActual] = fragmentoActual;
    }

    @Override
    public void onBackPressed() {

    }
}
