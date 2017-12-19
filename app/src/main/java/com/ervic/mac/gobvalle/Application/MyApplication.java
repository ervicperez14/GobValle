package com.ervic.mac.gobvalle.Application;

import android.app.Application;
import android.support.design.widget.BottomNavigationView;

import com.ervic.mac.gobvalle.Models.BottomMenu;
import com.ervic.mac.gobvalle.Models.Data;
import com.ervic.mac.gobvalle.Models.DataType;
import com.ervic.mac.gobvalle.Models.DataUser;
import com.ervic.mac.gobvalle.Models.RequestTypeResponse;
import com.ervic.mac.gobvalle.Models.Types;
import com.ervic.mac.gobvalle.Models.careServicesMenu;
import com.ervic.mac.gobvalle.Models.leftMenu;

import java.util.List;

/**
 * Created by ervic on 11/24/17.
 */

public class MyApplication extends Application {
    public static MyApplication sInstance;
    public static Boolean restarActivity = true;
    public static String tokenFirebase;
    public static DataUser _dataUser;
    public static Data data = null;
    public static DataType types;
    public static String token;
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Data getData() {
        return data;
    }

    public static void setData(Data data) {
        MyApplication.data = data;
    }

    public static DataType getTypes() {
        return types;
    }

    public static void setTypes(DataType types) {
        MyApplication.types = types;
    }

    public static DataUser get_dataUser() {
        return _dataUser;
    }

    public static void set_dataUser(DataUser _dataUser) {
        MyApplication._dataUser = _dataUser;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MyApplication.token = token;
    }

    public static String getTokenFirebase() {
        return tokenFirebase;
    }

    public static void setTokenFirebase(String tokenFirebase) {
        MyApplication.tokenFirebase = tokenFirebase;
    }
}
