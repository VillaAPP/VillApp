package studio.waterwell.villaapp.BD;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Path;

import java.util.ArrayList;

import studio.waterwell.villaapp.Modelo.MiOpinion;

/**
 * Created by Efecto Dopler on 11/05/2017.
 */

public class DAOOpinion {
    private SQLiteDatabase bd;
    private Opiniones opinion;

    public DAOOpinion(Opiniones opinion){
        this.opinion = opinion;
    }

    private void abrirBDLectura(){
        bd = opinion.getReadableDatabase();
    }

    private void abriBDEscritura(){
        bd = opinion.getWritableDatabase();
    }

    private void cerrarBD(){
        bd.close();
    }

    // Comprueba si hay opiniones en la bd interna
    public boolean existenOpiniones(){
        boolean ok = false;

        abrirBDLectura();

        Cursor cursor = bd.rawQuery("SELECT count(*) FROM Opiniones", null);
        if(cursor.moveToFirst()){
            int aux = cursor.getInt(0);

            if(aux != 0)
                ok = true;
        }

        cursor.close();;
        cerrarBD();;
        return ok;
    }

    // Cargo todas las opiniones del usuario
    public ArrayList<MiOpinion> cargarOpiniones(){
        ArrayList<MiOpinion> lista = new ArrayList<MiOpinion>();

        abrirBDLectura();

        Cursor cursor = bd.rawQuery("SELECT * FROM Opiniones", null);

        if(cursor.moveToFirst()){
            do{
                MiOpinion opinion = new MiOpinion(cursor.getString(0), cursor.getInt(1), cursor.getString(2));
                lista.add(opinion);
            }while(cursor.moveToNext());
        }


        cursor.close();
        cerrarBD();

        return lista;
    }

    // Guarda una lista de opiniones en la bd interna
    public void guardarOpiniones(ArrayList<MiOpinion> opiniones){
        abriBDEscritura();

        ContentValues valores = new ContentValues();

        for(int i = 0; i < opiniones.size(); i++){
            valores.put("idLugar", opiniones.get(i).getId());
            valores.put("rate", opiniones.get(i).getRate());
            valores.put("opinion", opiniones.get(i).getOpinion());
            bd.insert("Opiniones",null, valores);
        }

        cerrarBD();
    }

    // Guarda una sola opinion en la bdi nterna
    public void guardarOpinion(MiOpinion opinion){
        abriBDEscritura();

        ContentValues valores = new ContentValues();
        valores.put("idLugar", opinion.getId());
        valores.put("rate", opinion.getRate());
        valores.put("opinion", opinion.getOpinion());

        bd.insert("Opiniones",null, valores);

        cerrarBD();
    }
}
