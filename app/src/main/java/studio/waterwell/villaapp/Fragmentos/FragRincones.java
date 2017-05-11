package studio.waterwell.villaapp.Fragmentos;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import studio.waterwell.villaapp.Modelo.AdaptadorLista;
import studio.waterwell.villaapp.Modelo.ICambios;
import studio.waterwell.villaapp.Modelo.Lugar;
import studio.waterwell.villaapp.R;


public class FragRincones extends Fragment {
    private ICambios cambios;
    private ListView lista;
    private AdaptadorLista adaptador;
    public FragRincones() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragRincones newInstance(ArrayList<Lugar> lugares) {
        FragRincones fragment = new FragRincones();
        Bundle args = new Bundle();
        args.putParcelableArrayList("lugares", lugares);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_rincones, container, false);
        lista = (ListView) v.findViewById(R.id.listas_rincones);

        ArrayList<Lugar> aux = getArguments().getParcelableArrayList("lugares");

        adaptador = new AdaptadorLista(getActivity(), aux );
        lista.setAdapter(adaptador);

        // Fijo el evento de que cuando clico en un objeto de la lista devuelvo sus coordenadas para que el mapa se centre en él
        lista.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lugar lugar = (Lugar) parent.getItemAtPosition(position);
                cambios.cambiarCamara(lugar.obtenerCoordenadas());
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cambios = (ICambios) getActivity();
    }
}
