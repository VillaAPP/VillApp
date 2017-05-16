package studio.waterwell.villaapp.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import studio.waterwell.villaapp.Controlador.ControladorLugar;
import studio.waterwell.villaapp.Controlador.ControladorOpiniones;
import studio.waterwell.villaapp.Modelo.AdaptadorOpiniones;
import studio.waterwell.villaapp.Modelo.ILugar;
import studio.waterwell.villaapp.Modelo.IOpiniones;
import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.Modelo.Opinion;
import studio.waterwell.villaapp.R;

public class fragRincon extends Fragment {

    private Button atras;
    private Button opinar;
    private Button ruta;
    private TextView nombre;
    private TextView direccion;
    private TextView descripcion;
    private TextView rateTotal;
    private ListView lista;
    private RelativeLayout layoutCara;
    private RelativeLayout layoutDatos;
    private AdaptadorOpiniones adaptador;
    private ImageView imagenLugar;

    private ControladorOpiniones controladorOpiniones;
    private  ArrayList<Opinion> opiniones;
    private IOpiniones iOpiniones;
    private Lugar lugar;
    private LatLng misCoordenadas;

    private ControladorLugar controladorLugar;
    private ILugar ilugar;

    public fragRincon() {
        // Required empty public constructor
    }


    public static fragRincon newInstance(Lugar lugar, double lat, double lng, boolean existe) {
        fragRincon fragment = new fragRincon();
        Bundle args = new Bundle();
        args.putParcelable("lugar", lugar);
        args.putDouble("latitud", lat);
        args.putDouble("longitud", lng);
        args.putBoolean("existe", existe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            lugar = getArguments().getParcelable("lugar");
            opiniones = new ArrayList<Opinion>();

            double latAux = getArguments().getDouble("latitud");
            double lngAux = getArguments().getDouble("longitud");

            misCoordenadas = new LatLng(latAux, lngAux);

            if(lugar.getNumOpiniones() != 0){
                opiniones = lugar.getOpiniones();
            }

            adaptador = new AdaptadorOpiniones(getActivity(), opiniones);
            iOpiniones = (IOpiniones) getActivity();
            controladorOpiniones = new ControladorOpiniones(getContext(), iOpiniones);
            controladorOpiniones.getId(lugar.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        boolean existe = getArguments().getBoolean("existe");

        View v = inflater.inflate(R.layout.fragment_rincon, container, false);
        cargarVista(v, existe);

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        iOpiniones = null;
    }

    // TODO: Falta cargar la foto del lugar.
    private void cargarVista(View v, boolean existe){

        opinar = (Button) v.findViewById(R.id.lugar_opinion);

        if(existe)
            opinar.setText(R.string.lugar_opinion2);

        ruta = (Button) v.findViewById(R.id.lugar_ruta);

        nombre = (TextView) v.findViewById(R.id.lugar_nombre);
        direccion = (TextView) v.findViewById(R.id.lugar_direccion);
        descripcion = (TextView) v.findViewById(R.id.lugar_descripcion);
        rateTotal = (TextView) v.findViewById(R.id.lugar_rate);
        lista = (ListView) v.findViewById(R.id.lista_opiniones);

        layoutCara = (RelativeLayout) v.findViewById(R.id.layout_carga);
        layoutDatos = (RelativeLayout) v.findViewById(R.id.layout_lista);

        calcularRate();

        nombre.setText(lugar.getNombre());
        direccion.setText(lugar.getDireccion());

        descripcion.setText(lugar.getDescripcion());

        lista.setAdapter(adaptador);

        // Solo si no hay opiniones del sitio miramos en la BD externa
        if(lugar.getNumOpiniones() == 0)
            controladorOpiniones.obtenerOpiniones();

            // Si hay opiniones no carga del controlador nada
        else{
            layoutCara.setVisibility(View.INVISIBLE);
            layoutDatos.setVisibility(View.VISIBLE);
        }

        ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOpiniones.cargarRuta();
            }
        });

        opinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOpiniones.darOpinion(misCoordenadas, lugar.obtenerCoordenadas());
            }
        });

        /*
        Cargar la imagen
         */
        this.imagenLugar = (ImageView) v.findViewById(R.id.lugar_imagen);
        this.controladorLugar = new ControladorLugar(getContext(), this.lugar);
        this.ilugar = (ILugar) getActivity();
        Bitmap imagenLugar = this.controladorLugar.cargarImagen(this.lugar.getId());
        if(imagenLugar == null) {
            this.controladorLugar.cargarImagenLugar(this.ilugar);
        }
        else {
            this.imagenLugar.setImageBitmap(imagenLugar);
        }


    }

    public void cargarImagen(Bitmap imagen_procesar) {
        this.controladorLugar.guardarImagen(this.lugar.getId(), imagen_procesar);
        this.imagenLugar.setImageBitmap(imagen_procesar);
        //Debería setear la imagen aquí, pero el comportamiento es errático.
        //this.lugar.setImagen(imagen_procesar);
    }

    public void cargarOpiniones(ArrayList<Opinion> opiniones) {
        this.opiniones = opiniones;
        lugar.setOpiniones(opiniones);
        adaptador.setLista(opiniones);
        adaptador.notifyDataSetChanged();

        layoutCara.setVisibility(View.INVISIBLE);
        layoutDatos.setVisibility(View.VISIBLE);

        calcularRate();
    }

    private void calcularRate(){
        int rate = 0, i;
        for(i = 0; i < opiniones.size(); i++){
            rate += opiniones.get(i).getRate();
        }

        if(i > 0)
            rateTotal.setText("Puntuacion :" + Integer.toString(rate/i));

    }



}
