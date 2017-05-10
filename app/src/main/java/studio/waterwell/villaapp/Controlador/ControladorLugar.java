package studio.waterwell.villaapp.Controlador;

import android.content.Context;

import java.util.ArrayList;

import studio.waterwell.villaapp.BD.DAOLugar;
import studio.waterwell.villaapp.Modelo.Lugar;

/**
 * Created by AdeFr on 10/05/2017.
 */

public class ControladorLugar {


    private ArrayList<Lugar> lugares;
    private DAOLugar daoLugar;
    private Context contexto;


    public ControladorLugar(Context contexto) {
        this.contexto = contexto;
        this.daoLugar = new DAOLugar(contexto);
        this.lugares = daoLugar.getLugares();
    }

    public ArrayList<Lugar> getLugares() {
        return lugares;
    }


}
