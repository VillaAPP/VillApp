package studio.waterwell.villaapp.Controlador;

import android.content.Context;

import studio.waterwell.villaapp.BD.DAOUsuario;
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

}
