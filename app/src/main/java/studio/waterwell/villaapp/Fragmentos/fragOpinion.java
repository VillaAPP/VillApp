package studio.waterwell.villaapp.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studio.waterwell.villaapp.Controlador.ControladorOpiniones;
import studio.waterwell.villaapp.Modelo.ICambios;
import studio.waterwell.villaapp.Modelo.IOpiniones;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;


public class fragOpinion extends Fragment {

    private IOpiniones iOpiniones;
    private ControladorOpiniones controladorOpiniones;
    private Usuario usuario;
    private boolean existeOpinion;

    public fragOpinion() {
        // Required empty public constructor
    }

    public static fragOpinion newInstance(Usuario usuario, boolean opinion) {
        fragOpinion fragment = new fragOpinion();
        Bundle args = new Bundle();
        args.putParcelable("usuario", usuario);
        args.putBoolean("existe", opinion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            existeOpinion = getArguments().getBoolean("existe");
            usuario = getArguments().getParcelable("usuario");
        }

        iOpiniones = (IOpiniones) getActivity();
        controladorOpiniones = new ControladorOpiniones(getActivity(),iOpiniones);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_opinion, container, false);

        cargarVista(v);

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        iOpiniones = null;
    }


    // TODO: Por hacer todo
    private void cargarVista(View v){}

}
