package com.ervic.mac.gobvalle.Models;

/**
 * Created by ervic on 12/25/17.
 */

public class ResponseProcess {
    public data data;
    public static class data{
        public String type_process;
        public String user_nombre;
        public String user_numero_identificacion;
        public String user_correo_electronico;
        public String user_telefono;
        public String fecha_cita;
        public String hora_cita;
        public String tipo_cita;
        public String tipo_solicitud;
        public String estado;
        public String passport_referencia_pago;
        public String passport_banco_pago;
        public String passport_sucursal_donde_pago;
        public String passport_tipo_pasaporte;
        public String passport_estado;
        public boolean validacionCancel;
        public String id_cita;
    }
}
