package es.upm.miw.SolitarioCelta;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {
    private static final String DB_NAME = JuegoContract.JuegoEntry.TABLE_NAME + ".db";
    private static final int DB_VERSION = 1;

    public DBManager(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + JuegoContract.JuegoEntry.TABLE_NAME + " ("
                + JuegoContract.JuegoEntry.COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + JuegoContract.JuegoEntry.COL_NAME_NOMBRE      + " TEXT, "
                + JuegoContract.JuegoEntry.COL_FICHAS + " INTEGER,"
                + JuegoContract.JuegoEntry.COL_DATE     + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + JuegoContract.JuegoEntry.TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public long addItem(Puntuacion puntuacion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(JuegoContract.JuegoEntry.COL_NAME_NOMBRE, puntuacion.getName());
        cv.put(JuegoContract.JuegoEntry.COL_FICHAS, puntuacion.getFichas());
        cv.put(JuegoContract.JuegoEntry.COL_DATE,puntuacion.getDate());
        return db.insert(JuegoContract.JuegoEntry.TABLE_NAME, null, cv);
    }


}
