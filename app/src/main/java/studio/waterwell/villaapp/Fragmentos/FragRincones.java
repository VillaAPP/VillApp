package studio.waterwell.villaapp.Fragmentos;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import studio.waterwell.villaapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragRincones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragRincones extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ICambios cambios;

    private Button boton1;
    private Button boton2;
    private Button boton3;


    public FragRincones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragRincones.
     */
    // TODO: Rename and change types and number of parameters
    public static FragRincones newInstance(String param1, String param2) {
        FragRincones fragment = new FragRincones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragRincones newInstance() {
        FragRincones fragment = new FragRincones();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rincones, container, false);

        boton1 = (Button) v.findViewById(R.id.ubicacion1);
        boton2 = (Button) v.findViewById(R.id.ubicacion2);
        boton3 = (Button) v.findViewById(R.id.ubicacion3);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambios.mandarCoordenadas(new LatLng(40.39991817, -3.6941729));
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambios.mandarCoordenadas(new LatLng(40.4166635, -3.7041687));
            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambios.mandarCoordenadas(new LatLng(40.4072103, -3.6945893));
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
