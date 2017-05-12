package studio.waterwell.villaapp.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import studio.waterwell.villaapp.Controlador.ControladorOpiniones;
import studio.waterwell.villaapp.Modelo.AdaptadorOpiniones;
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

    private ControladorOpiniones controladorOpiniones;
    private  ArrayList<Opinion> opiniones;
    private IOpiniones iOpiniones;
    private Lugar lugar;

    public fragRincon() {
        // Required empty public constructor
    }


    public static fragRincon newInstance(Lugar lugar) {
        fragRincon fragment = new fragRincon();
        Bundle args = new Bundle();
        args.putParcelable("lugar", lugar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            lugar = getArguments().getParcelable("lugar");
            opiniones = new ArrayList<Opinion>();

            if(lugar.getNumOpiniones() != 0){
                opiniones = lugar.getOpiniones();
                calcularRate();
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

        View v = inflater.inflate(R.layout.fragment_rincon, container, false);
        cargarVista(v);

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        iOpiniones = null;
    }

    // TODO: Falta cargar la foto del lugar.
    private void cargarVista(View v){
        atras = (Button) v.findViewById(R.id.lugar_atras);
        opinar = (Button) v.findViewById(R.id.lugar_opinion);
        ruta = (Button) v.findViewById(R.id.lugar_ruta);

        nombre = (TextView) v.findViewById(R.id.lugar_nombre);
        direccion = (TextView) v.findViewById(R.id.lugar_direccion);
        descripcion = (TextView) v.findViewById(R.id.lugar_descripcion);
        rateTotal = (TextView) v.findViewById(R.id.lugar_rate);
        lista = (ListView) v.findViewById(R.id.lista_opiniones);

        layoutCara = (RelativeLayout) v.findViewById(R.id.layout_carga);
        layoutDatos = (RelativeLayout) v.findViewById(R.id.layout_lista);

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

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOpiniones.irAtras();
            }
        });

        ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOpiniones.cargarRuta();
            }
        });

        opinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOpiniones.darOpinion();
            }
        });
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
