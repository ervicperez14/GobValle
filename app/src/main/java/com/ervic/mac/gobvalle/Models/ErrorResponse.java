package com.ervic.mac.gobvalle.Models;

/**
 * Created by ervic on 12/25/17.
 */

public class ErrorResponse {
    Error error;

    public static class Error {
        Data data;

        public static class Data {
            String message;
        }
    }
}
