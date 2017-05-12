package studio.waterwell.villaapp.Controlador;

import android.content.Context;
import studio.waterwell.villaapp.BD.WebService.CargarOpiniones;
import studio.waterwell.villaapp.Modelo.IOpiniones;

/**
 * Created by Efecto Dopler on 11/05/2017.
 */

public class ControladorOpiniones {
    private CargarOpiniones cargarOpiniones;
    private Context contexto;
    private IOpiniones iOpiniones;
    private String idLugar;
    public ControladorOpiniones(Context contexto, IOpiniones iOpiniones) {
        this.iOpiniones = iOpiniones;
        this.contexto = contexto;

    }

    public void getId(String id){
        this.idLugar = id;
    }

    public void obtenerOpiniones(){
        this.cargarOpiniones = new CargarOpiniones(this.iOpiniones);
        cargarOpiniones.execute(idLugar);
    }

    // TODO: Mandar la opinion tanto a la bd externa como interna.
    public void mandarOpinion(){}
}
