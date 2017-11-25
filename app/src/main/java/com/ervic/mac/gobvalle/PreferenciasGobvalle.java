package com.ervic.mac.gobvalle;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;

/**
 * Created by ervic on 11/24/17.
 */

public class PreferenciasGobvalle {
    private static final String PREFERENCES_FILE = "gobvalle_prefs";
    Context context;


    public PreferenciasGobvalle(Context context) {
        this.context = context;
    }

    public void savejsonString(String nombre, String cadena){
        SharedPreferences.Editor editor = this.context.getSharedPreferences(PREFERENCES_FILE, 0).edit();
        editor.putString(nombre, cadena);
        editor.apply();
    }

    public String readjsonString(String nombre,String valorDefault){
        return this.context.getSharedPreferences(PREFERENCES_FILE, 0).getString(nombre, valorDefault);
    }

    public void saveElement(String nombre, Boolean valor){
        SharedPreferences.Editor editor = this.context.getSharedPreferences(PREFERENCES_FILE, 0).edit();
        editor.putBoolean(nombre, valor);
        editor.apply();
    }

    public Boolean readElement(String nombre, Boolean valorDefault){
        return this.context.getSharedPreferences(PREFERENCES_FILE,0).getBoolean(nombre, valorDefault);
    }

}
