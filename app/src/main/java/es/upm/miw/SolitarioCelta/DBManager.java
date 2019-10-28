package es.upm.miw.SolitarioCelta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

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
                + JuegoContract.JuegoEntry.COL_DATE     + " TEXT,"
                + JuegoContract.JuegoEntry.COL_CHRON + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + JuegoContract.JuegoEntry.TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public long AddItem(Puntuacion puntuacion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(JuegoContract.JuegoEntry.COL_NAME_NOMBRE, puntuacion.getName());
        cv.put(JuegoContract.JuegoEntry.COL_FICHAS, puntuacion.getFichas());
        cv.put(JuegoContract.JuegoEntry.COL_DATE,puntuacion.getDate());
        cv.put(JuegoContract.JuegoEntry.COL_CHRON,puntuacion.getChronometer());
        return db.insert(JuegoContract.JuegoEntry.TABLE_NAME, null, cv);
    }


    public ArrayList<Puntuacion> GetAll() {
        String query = "SELECT * FROM " + JuegoContract.JuegoEntry.TABLE_NAME + " ORDER BY " + JuegoContract.JuegoEntry.COL_FICHAS;
        ArrayList<Puntuacion> listScore = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Puntuacion score = new Puntuacion();
                score.setid(cursor.getInt(cursor.getColumnIndex(JuegoContract.JuegoEntry.COL_NAME_ID)));
                score.setName(cursor.getString(cursor.getColumnIndex(JuegoContract.JuegoEntry.COL_NAME_NOMBRE)));
                score.setFichas(cursor.getInt(cursor.getColumnIndex(JuegoContract.JuegoEntry.COL_FICHAS)));
                score.setDate(cursor.getString(cursor.getColumnIndex(JuegoContract.JuegoEntry.COL_DATE)));
                score.setChronometer(cursor.getString(cursor.getColumnIndex(JuegoContract.JuegoEntry.COL_CHRON)));
                listScore.add(score);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return listScore;
    }

    public void Delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+JuegoContract.JuegoEntry.TABLE_NAME);
    }

    public long Count() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, JuegoContract.JuegoEntry.TABLE_NAME);
    }


}
