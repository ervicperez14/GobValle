package com.ervic.mac.gobvalle.Application;

import android.app.Application;

import com.ervic.mac.gobvalle.Models.BottomMenu;
import com.ervic.mac.gobvalle.Models.Data;
import com.ervic.mac.gobvalle.Models.DataType;
import com.ervic.mac.gobvalle.Models.RequestTypeResponse;
import com.ervic.mac.gobvalle.Models.Types;
import com.ervic.mac.gobvalle.Models.careServicesMenu;
import com.ervic.mac.gobvalle.Models.leftMenu;

import java.util.List;

/**
 * Created by ervic on 11/24/17.
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;
    private static Data data;
    private static DataType types;
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
}
