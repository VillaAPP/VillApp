package studio.waterwell.villaapp.Modelo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import studio.waterwell.villaapp.R;

/**
 * Created by Efecto Dopler on 10/05/2017.
 */

public class AdaptadorLista extends BaseAdapter {
    private Activity actividad;
    private ArrayList<Lugar> lista;

    public AdaptadorLista(Activity actividad, ArrayList<Lugar> lugares){
        lista = lugares;
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
            v = inflater.inflate(R.layout.item_rincon, null);
        }

        TextView nombre = (TextView) v.findViewById(R.id.item_nombre);
        TextView direccion = (TextView) v.findViewById(R.id.item_direccion);

        nombre.setText(lista.get(position).getNombre());
        direccion.setText(lista.get(position).getDireccion());

        return v;
    }
}
