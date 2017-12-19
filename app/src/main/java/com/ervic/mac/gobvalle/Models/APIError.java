package com.ervic.mac.gobvalle.Models;

/**
 * Created by ervic on 12/15/17.
 */

public class APIError {

    private int statusCode;
    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}