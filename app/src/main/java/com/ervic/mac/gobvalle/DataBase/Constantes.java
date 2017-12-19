package com.ervic.mac.gobvalle.DataBase;

import android.provider.BaseColumns;

/**
 * Created by ervic on 11/30/17.
 */

public class Constantes {
    public Constantes() {
    }

    public static class Tabla_usuario implements BaseColumns

    {
        public static final String TABLA_USUARIO = "usuario";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_NUMERO_IDENTIFICACION = "identificacion";
        public static final String COLUMNA_FECHA_PAGO = "fecha_pago";
        public static final String COLUMNA_TIPO_SOLICITUD = "tipo_solicitud";
        public static final String COLUMNA_TELEFONO = "telefono";
        public static final String COLUMNA_CORREO = "correo";


    }

}
