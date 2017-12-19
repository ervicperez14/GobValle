package com.ervic.mac.gobvalle.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ervic.mac.gobvalle.DataBase.*;
/**
 * Created by ervic on 11/30/17.
 */

public class AdminOpenHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pasaport";

    public static final String SQL_CREATE_TABLE_USER = "CREATE TABLE " +
            Constantes.Tabla_usuario.TABLA_USUARIO + " (" +
            Constantes.Tabla_usuario.COLUMNA_ID + " INTEGER PRIMARY KEY, " +
            Constantes.Tabla_usuario.COLUMNA_NUMERO_IDENTIFICACION+ TEXT_TYPE + COMMA_SEP +
            Constantes.Tabla_usuario.COLUMNA_FECHA_PAGO  + TEXT_TYPE + COMMA_SEP +
            Constantes.Tabla_usuario.COLUMNA_TIPO_SOLICITUD  + TEXT_TYPE + COMMA_SEP +
            Constantes.Tabla_usuario.COLUMNA_TELEFONO  + TEXT_TYPE + COMMA_SEP +
            Constantes.Tabla_usuario.COLUMNA_CORREO  + TEXT_TYPE + " ) ";

    public static final String SQL_DELETE_TABLE_USER = "DROP TABLE IF EXISTS " + Constantes.Tabla_usuario.TABLA_USUARIO;

    public AdminOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_USER);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
