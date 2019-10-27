package es.upm.miw.SolitarioCelta;

import android.provider.BaseColumns;

public final class JuegoContract {
    public static class JuegoEntry implements BaseColumns {
        public static final String TABLE_NAME = "puntuacion";
        public static final String COL_NAME_ID = BaseColumns._ID;
        public static final String COL_NAME_NOMBRE = "nombre";
        public static final String COL_FICHAS = "fichas";
        public static final String COL_DATE     = "date";
    }
}
