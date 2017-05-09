package studio.waterwell.villaapp.BD;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Efecto Dopler on 02/05/2017.
 */

public class DAOUsuario {
    private SQLiteDatabase bd;
    private Usuario usuario;

    public DAOUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    private void abrirBDLectura(){
        bd = usuario.getReadableDatabase();
    }

    private void abriBDEscritura(){
        bd = usuario.getWritableDatabase();
    }

    private void cerrarBD(){
        bd.close();
    }

    /* Esta clase devuelve true si existe un usuario en la bd interna
     * y false si no hay nada almacenado
     */
    public boolean existeUsuario(){
        boolean ok = false;

        abrirBDLectura();

        Cursor cursor = bd.rawQuery("SELECT count(*) FROM Usuario", null);
        if(cursor.moveToFirst()){
            int aux = cursor.getInt(0);

            if(aux != 0)
                ok = true;
        }

        cursor.close();;
        cerrarBD();;
        return ok;
    }

    public studio.waterwell.villaapp.Modelo.Usuario cargarUsuario(){
        studio.waterwell.villaapp.Modelo.Usuario usuario = null;

        abrirBDLectura();

        Cursor cursor = bd.rawQuery("SELECT * FROM Usuario", null);

        if(cursor.moveToFirst())
            usuario = new studio.waterwell.villaapp.Modelo.Usuario(cursor.getString(0), cursor.getString(1));


        cursor.close();
        cerrarBD();

        return usuario;
    }

    public void guardarUsuario(studio.waterwell.villaapp.Modelo.Usuario usuario){
        abriBDEscritura();
        ContentValues valores = new ContentValues();
        valores.put("userName", usuario.getUserName());
        valores.put("password", usuario.getPassword());
        bd.insert("Usuario",null, valores);
        cerrarBD();
    }

    //TODO: Insertar y borrar un usuario
}
