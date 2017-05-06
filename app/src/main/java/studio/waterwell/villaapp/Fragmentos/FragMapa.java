package studio.waterwell.villaapp.Fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import studio.waterwell.villaapp.R;

public class FragMapa extends Fragment {

    private Mapa mapa;
    private Button boton;
    private Button boton2;

    public FragMapa() {
        // Required empty public constructor
    }

    public static FragMapa newInstance() {
        FragMapa fragment = new FragMapa();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mapa2, container, false);

        mapa = Mapa.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.frag_mapa, mapa)
                .commit();

        boton = (Button) v.findViewById(R.id.btn_tmapa);

        boton2 = (Button) v.findViewById(R.id.btn_pos);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapa.cambiarMapa();
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapa.volverUbicacion();
            }
        });

        return v;
    }

}
