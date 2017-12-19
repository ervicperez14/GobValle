package com.ervic.mac.gobvalle.Models;

/**
 * Created by ervic on 11/25/17.
 */

public class DataUser {
    public static String identificacion;
    public static String pago;
    public static String solicitud;
    public static String telefono;
    public static String correo;

    public static String getIdentificacion() {
        return identificacion;
    }

    public static void setIdentificacion(String identificacion) {
        DataUser.identificacion = identificacion;
    }

    public static String getPago() {
        return pago;
    }

    public static void setPago(String pago) {
        DataUser.pago = pago;
    }

    public static String getSolicitud() {
        return solicitud;
    }

    public static void setSolicitud(String solicitud) {
        DataUser.solicitud = solicitud;
    }

    public static String getTelefono() {
        return telefono;
    }

    public static void setTelefono(String telefono) {
        DataUser.telefono = telefono;
    }

    public static String getCorreo() {
        return correo;
    }

    public static void setCorreo(String correo) {
        DataUser.correo = correo;
    }
}
