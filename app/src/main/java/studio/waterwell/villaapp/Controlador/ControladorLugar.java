package studio.waterwell.villaapp.Controlador;

import android.content.Context;

import java.util.ArrayList;

import studio.waterwell.villaapp.BD.DAOLugar;
import studio.waterwell.villaapp.BD.WebService.CargarImagen;
import studio.waterwell.villaapp.Modelo.ILugar;
import studio.waterwell.villaapp.Modelo.Lugar;

/**
 * Created by AdeFr on 10/05/2017.
 */

public class ControladorLugar {
    private ArrayList<Lugar> lugares;
    private Lugar lugar;
    private DAOLugar daoLugar;
    private Context contexto;

    /*Para cargar todos los lugares*/
    public ControladorLugar(Context contexto) {
        this.contexto = contexto;
        this.daoLugar = new DAOLugar(this.contexto);
        this.lugares = daoLugar.getLugares();
    }

    /*Para cargar un solo lugar*/
    public ControladorLugar(Context contexto, Lugar lugar) {
        this.contexto = contexto;
        this.lugar = lugar;
    }

    public ArrayList<Lugar> getLugares() {
        return lugares;
    }

    public void cargarImagenLugar(ILugar ilugar) {

        CargarImagen hilo = new CargarImagen(ilugar);
        hilo.execute(this.lugar.getRutaImagen());
    }
}
