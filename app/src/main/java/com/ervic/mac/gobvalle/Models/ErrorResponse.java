package com.ervic.mac.gobvalle.Models;

/**
 * Created by ervic on 12/25/17.
 */

public class ErrorResponse {
    public Error error;

    public static class Error {
        public String code;
        public String message;
        public Details details;

        public static class Details {
            public String prueba;
        }
    }
}
