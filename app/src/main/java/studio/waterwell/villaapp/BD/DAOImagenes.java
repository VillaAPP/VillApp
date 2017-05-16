package studio.waterwell.villaapp.BD;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by AdeFr on 16/05/2017.
 */

public class DAOImagenes {
    private SQLiteDatabase bd;
    private Imagenes imagenes;

    public DAOImagenes(Imagenes imagenes){
        this.imagenes = imagenes;
    }

    private void abrirBDLectura(){
        bd = imagenes.getReadableDatabase();
    }

    private void abriBDEscritura(){
        bd = imagenes.getWritableDatabase();
    }

    private void cerrarBD(){
        bd.close();
    }


    public Bitmap cargarImagen(String idLugar){
        Bitmap imagen = null;

        abrirBDLectura();

        String sentencia = "SELECT imagen FROM Imagenes WHERE idLugar = '" + idLugar + "'";
        Cursor cursor = bd.rawQuery(sentencia, null);

        if(cursor.moveToFirst()) {
            byte[] aux = cursor.getBlob(0);
            imagen = BitmapFactory.decodeByteArray(aux, 0 ,aux.length);
        }


        cursor.close();
        cerrarBD();

        return imagen;
    }

    public void guardarImagen(String idLugar, Bitmap imagen){
        abriBDEscritura();
        ContentValues valores = new ContentValues();

        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
        byte[] bitmapdata = blob.toByteArray();

        valores.put("idLugar", idLugar);
        valores.put("imagen", bitmapdata);
        bd.insert("Imagenes",null, valores);
        cerrarBD();
    }

    public void borrarImagenes(){
        abriBDEscritura();
        bd.delete("Imagenes", null, null);
        cerrarBD();
    }

}
