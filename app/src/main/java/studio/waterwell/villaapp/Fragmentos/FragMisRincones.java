package studio.waterwell.villaapp.Fragmentos;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import studio.waterwell.villaapp.Modelo.ICambios;
import studio.waterwell.villaapp.R;


public class FragMisRincones extends Fragment {

    private String txt;
    private EditText t;
    private Button b;
    private ICambios cambios;

    public FragMisRincones() {
        // Required empty public constructor
    }

    public static FragMisRincones newInstance() {
        FragMisRincones fragment = new FragMisRincones();
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
        View v = inflater.inflate(R.layout.fragment_mis_rincones, container, false);
        t = (EditText) v.findViewById(R.id.pruebas);
        b = (Button) v.findViewById(R.id.btn_pruebas);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = t.getText().toString();
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
