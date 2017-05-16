package studio.waterwell.villaapp.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AdeFr on 16/05/2017.
 */

public class Imagenes extends SQLiteOpenHelper {
    private String create ="CREATE TABLE Imagenes (idLugar TEXT, imagen BLOB)";

    public Imagenes(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Aquí no se hace nada de momento
    }
}
