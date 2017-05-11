package studio.waterwell.villaapp.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Efecto Dopler on 11/05/2017.
 */

public class Opiniones extends SQLiteOpenHelper{
    private String create ="CREATE TABLE Opiniones (idLugar TEXT, rate INTEGER, opinion TEXT)";


    public Opiniones(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
