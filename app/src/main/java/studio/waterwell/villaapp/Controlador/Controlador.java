package studio.waterwell.villaapp.Controlador;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import studio.waterwell.villaapp.BD.DAOUsuario;
import studio.waterwell.villaapp.BD.ObtenerRuta;
import studio.waterwell.villaapp.Modelo.ICambios;
import studio.waterwell.villaapp.Modelo.Usuario;

/**
 * Created by Efecto Dopler on 02/05/2017.
 */

public class Controlador {
    private Context contexto;

    public Controlador(Context c){
        contexto = c;
    }

    public boolean existeUsuario(){
        studio.waterwell.villaapp.BD.Usuario user = new studio.waterwell.villaapp.BD.Usuario(contexto, "Usuario", null, 1);
        DAOUsuario DAOUsuario = new DAOUsuario(user);
        return DAOUsuario.existeUsuario();
    }

    public Usuario cargarUsuario(){
        studio.waterwell.villaapp.BD.Usuario user = new studio.waterwell.villaapp.BD.Usuario(contexto, "Usuario", null, 1);
        DAOUsuario DAOUsuario = new DAOUsuario(user);
        return DAOUsuario.cargarUsuario();
    }

    public void guardarUsuarioBDInterna(Usuario usuario){
        studio.waterwell.villaapp.BD.Usuario user = new studio.waterwell.villaapp.BD.Usuario(contexto, "Usuario", null, 1);
        DAOUsuario DAOUsuario = new DAOUsuario(user);
        DAOUsuario.guardarUsuario(usuario);
    }

    public void obtenerRuta(ICambios cambios, LatLng latOrigen, LatLng latDestino){
        ObtenerRuta ruta = new ObtenerRuta(cambios);

        String origen = latOrigen.latitude+","+latOrigen.longitude;
        String destino = latDestino.latitude+","+latDestino.longitude;

        ruta.execute(origen, destino);
    }

}
