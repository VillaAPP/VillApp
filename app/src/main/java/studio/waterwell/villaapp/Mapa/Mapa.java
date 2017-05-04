package studio.waterwell.villaapp.Mapa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.SupportMapFragment;

import studio.waterwell.villaapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mapa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mapa extends SupportMapFragment {
    // TODO: Rename parameter arguments, choose names that match



    public Mapa() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }


    // Este lo he creado de prueba
    public static Mapa newInstance() {
        Mapa fragment = new Mapa();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = super.onCreateView(inflater, container, savedInstanceState);

        return v;
    }

}
