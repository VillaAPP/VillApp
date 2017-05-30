package studio.waterwell.villaapp.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import studio.waterwell.villaapp.Controlador.ControladorOpiniones;
import studio.waterwell.villaapp.Modelo.ICambios;
import studio.waterwell.villaapp.Modelo.IOpiniones;
import studio.waterwell.villaapp.Modelo.MiOpinion;
import studio.waterwell.villaapp.Modelo.Usuario;
import studio.waterwell.villaapp.R;


public class fragOpinion extends Fragment {

    private IOpiniones iOpiniones;
    private ControladorOpiniones controladorOpiniones;
    private Usuario usuario;
    private String idLugar;
    private boolean existeOpinion;

    private EditText texto_opinion;
    private Button reset_button;
    private Button enviar_button;
    private RatingBar puntuacion;
    private TextView view_opinion;

    public fragOpinion() {
        // Required empty public constructor
    }

    public static fragOpinion newInstance(Usuario usuario, boolean opinion, String idLugar) {
        fragOpinion fragment = new fragOpinion();
        Bundle args = new Bundle();
        args.putParcelable("usuario", usuario);
        args.putBoolean("existe", opinion);
        args.putString("idLugar", idLugar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            this.existeOpinion = getArguments().getBoolean("existe");
            this.usuario = getArguments().getParcelable("usuario");
            this.idLugar = getArguments().getString("idLugar");

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

    private void cargarVista(View v){

        this.texto_opinion = (EditText) v.findViewById(R.id.opinion_texto);
        this.reset_button = (Button) v.findViewById(R.id.reset_button);
        this.enviar_button = (Button) v.findViewById(R.id.enviar_button);
        this.puntuacion = (RatingBar) v.findViewById(R.id.puntuacion);
        this.view_opinion = (TextView) v.findViewById(R.id.opinion_view);

        // Si existe ya una opinion
        if(this.existeOpinion)
            existeOpinion();

        // Si no existe una opinion
        else{
            this.reset_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    puntuacion.setRating(0);
                    texto_opinion.setText("");
                    Toast toast = Toast.makeText(getContext(), " Valores reiniciados", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            this.enviar_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float num = puntuacion.getRating();
                    String opinion = texto_opinion.getText().toString();
                    MiOpinion miOpinion = new MiOpinion(idLugar, (int)num, opinion);

                    controladorOpiniones.guardarOpinion(usuario, miOpinion);
                    usuario.addOpinion(miOpinion);
                    iOpiniones.darOpinion(miOpinion);
                }
            });
        }

    }

    private void existeOpinion(){
        this.reset_button.setEnabled(false);
        this.enviar_button.setEnabled(false);

        ArrayList<MiOpinion> listaOpiniones = this.usuario.getOpiniones();
        boolean encontrado = false;
        int i = 0;
        String opinion = "";
        int puntuacion = 0;
        while(!encontrado && i < listaOpiniones.size()) {
            if(listaOpiniones.get(i).getId().equals(this.idLugar)) {
                encontrado = true;
                opinion = listaOpiniones.get(i).getOpinion();
                puntuacion = listaOpiniones.get(i).getRate();
            }
            else
                i++;
        }

        this.texto_opinion.setVisibility(View.INVISIBLE);
        this.view_opinion.setVisibility(View.VISIBLE);
        this.view_opinion.setText(opinion);
        this.puntuacion.setRating((float)puntuacion/2);
        this.puntuacion.setEnabled(false);
        this.enviar_button.setVisibility(View.INVISIBLE);
        this.reset_button.setVisibility(View.INVISIBLE);
    }

    // Una vez se ha dado una opinion, eso se llama desde lugarClicado y pone la vista para leer una opinion
    public void modificarVista(){
        existeOpinion();
    }

}
