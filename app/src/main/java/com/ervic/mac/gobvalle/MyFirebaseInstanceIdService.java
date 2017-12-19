package com.ervic.mac.gobvalle;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ervic on 12/6/17.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private PreferenciasGobvalle my_preferences;
    private String FIREBASE_TOKEN = "TOKEN_FIREBASE";
    private String TAG = "TOKEN_FIREBASE";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


    }
}
