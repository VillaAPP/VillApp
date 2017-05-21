package studio.waterwell.villaapp.Controlador;

import android.content.Context;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import studio.waterwell.villaapp.BD.DAOImagenes;
import studio.waterwell.villaapp.BD.DAOOpinion;
import studio.waterwell.villaapp.BD.DAOUsuario;
import studio.waterwell.villaapp.BD.Imagenes;
import studio.waterwell.villaapp.BD.Opiniones;
import studio.waterwell.villaapp.BD.WebService.ObtenerRuta;
import studio.waterwell.villaapp.Modelo.ICambios;
import studio.waterwell.villaapp.Modelo.Usuario;

/**
 * Created by Efecto Dopler on 02/05/2017.
 */

public class Controlador {
    private Context contexto;

    public Controlador(Context c){
        contexto = c;
        Imagenes imagenes = new Imagenes(this.contexto, "Imagenes", null, 1);
        DAOImagenes daoImagenes = new DAOImagenes(imagenes);
        daoImagenes.borrarImagenes();
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
