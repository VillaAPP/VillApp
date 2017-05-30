package studio.waterwell.villaapp.Modelo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import studio.waterwell.villaapp.R;

/**
 * Created by Efecto Dopler on 11/05/2017.
 */

public class AdaptadorOpiniones extends BaseAdapter {
    private Activity actividad;
    private ArrayList<Opinion> lista;

    public AdaptadorOpiniones(Activity actividad, ArrayList<Opinion> opiniones){
        lista = opiniones;
        this.actividad = actividad;
    }
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        // Inflo la vista cogiendo el inflater del activity e inflando el layout de cada item
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) actividad.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_opinion, null);
        }

        TextView nombre = (TextView) v.findViewById(R.id.item_opinion_nombre);
        TextView opinion = (TextView) v.findViewById(R.id.item_opinion_opinion);
        TextView rate = (TextView) v.findViewById(R.id.item_opnion_rate) ;

        nombre.setText(lista.get(position).getUserName());
        opinion.setText(lista.get(position).getOpinion());
        String aux = Integer.toString(lista.get(position).getRate()*2);
        rate.setText(aux);

        return v;
    }

    public void setLista(ArrayList<Opinion> opiniones){
        lista = opiniones;
    }

}
