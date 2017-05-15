package studio.waterwell.villaapp.Controlador;

import android.content.Context;

import java.util.ArrayList;

import studio.waterwell.villaapp.BD.DAOOpinion;
import studio.waterwell.villaapp.BD.Opiniones;
import studio.waterwell.villaapp.BD.WebService.CargarOpiniones;
import studio.waterwell.villaapp.BD.WebService.CargarOpinionesUsuario;
import studio.waterwell.villaapp.Modelo.IMisOpiniones;
import studio.waterwell.villaapp.Modelo.IOpiniones;
import studio.waterwell.villaapp.Modelo.MiOpinion;
import studio.waterwell.villaapp.Modelo.Usuario;

/**
 * Created by Efecto Dopler on 11/05/2017.
 */

public class ControladorOpiniones {
    private CargarOpiniones cargarOpiniones;
    private Context contexto;
    private IOpiniones iOpiniones;
    private IMisOpiniones iMisOpiniones;
    private String idLugar;

    public ControladorOpiniones(Context contexto, IOpiniones iOpiniones) {
        this.iOpiniones = iOpiniones;
        this.contexto = contexto;

    }

    public ControladorOpiniones(Context contexto, IMisOpiniones iMisOpiniones) {
        this.contexto = contexto;
        this.iMisOpiniones = iMisOpiniones;
    }

    public void getId(String id){
        this.idLugar = id;
    }

    public void obtenerOpiniones(){
        this.cargarOpiniones = new CargarOpiniones(this.iOpiniones);
        cargarOpiniones.execute(idLugar);
    }

    // TODO: Falta guardar una nueva opinion en la bd interna y externa
    public void guardarOpinion(Usuario usuario){}

    public void obtenerOpinionesUsuarioExterna(Usuario usuario){
        CargarOpinionesUsuario cargarOpinionesUsuario = new CargarOpinionesUsuario(iMisOpiniones);
        cargarOpinionesUsuario.execute(usuario.getUserName());
    }

    public void obtenerOpinionesUsuarioInterna(Usuario usuario){
        Opiniones opiniones = new Opiniones(contexto, "Opiniones", null, 1);
        DAOOpinion daoOpinion = new DAOOpinion(opiniones);
        usuario.addOpiniones(daoOpinion.cargarOpiniones());
    }

    public boolean existenOpinionesInternas(){
        Opiniones opiniones = new Opiniones(contexto, "Opiniones", null, 1);
        DAOOpinion daoOpinion = new DAOOpinion(opiniones);
        return daoOpinion.existenOpiniones();
    }

    public void guardarOpinionesInterna(ArrayList<MiOpinion> lista){
        Opiniones opiniones = new Opiniones(contexto, "Opiniones", null, 1);
        DAOOpinion daoOpinion = new DAOOpinion(opiniones);
        daoOpinion.guardarOpiniones(lista);
    }
}
