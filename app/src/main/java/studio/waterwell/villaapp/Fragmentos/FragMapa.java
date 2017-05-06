package studio.waterwell.villaapp.Fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Map;

import studio.waterwell.villaapp.R;

public class FragMapa extends Fragment {

    private Mapa mapa;
    private Button boton;

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

        boton = (Button) v.findViewById(R.id.tipo_mapa);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapa.cambiarMapa();
            }
        });
        return v;
    }

}
