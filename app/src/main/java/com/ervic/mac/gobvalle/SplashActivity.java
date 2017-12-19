package com.ervic.mac.gobvalle;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.ervic.mac.gobvalle.API.MyApiService;
import com.ervic.mac.gobvalle.Models.APIError;
import com.ervic.mac.gobvalle.Models.DataType;
import com.ervic.mac.gobvalle.Models.DataUser;
import com.ervic.mac.gobvalle.Models.MenuResponse;
import com.ervic.mac.gobvalle.Application.*;
import com.ervic.mac.gobvalle.Models.RequestTypeResponse;
import com.ervic.mac.gobvalle.Models.ResponseToken;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private Context context = this;
    private String TAG_LOGGED = "LOGGED";
    private String TAG_MENU = "MENUS";
    private String TAG_DATA_MENU = "MENUS_DATA";
    private String TAG_REQUEST_TYPE ="REQUEST_TYPE";
    private String TAG_DATA_REQUEST_TYPE ="REQUEST_TYPE_DATA";
    private String TAG_TOKEN = "TOKEN";
    private String TAG_DATA_TOKEN = "TOKEN_DATA";
    private String TAG_ID ="ID";
    private String TAG_EMAIL = "EMAIL";
    private String TAG_PHONE = "PHONE";
    private String TAG_PAYMENT_DATE = "DATE";
    private String TAG_REQUEST = "REQUEST";
    final int REQUEST_MULTIPLE = 0;
    private PreferenciasGobvalle my_preferences;
    private MyApplication _app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        my_preferences = new PreferenciasGobvalle(this);
        _app = new MyApplication().getInstance();


            loadUser();

        


    }


    public void getMenus(){
        //Comprobamos si ya existen datos
        if(my_preferences.readElement(TAG_MENU,false)) {
            Log.e(TAG_MENU,"YES");
            //Extraemos los datos
            Gson gson = new Gson();
            String stored = my_preferences.readjsonString(TAG_DATA_MENU,null);
            if(stored!=null)
            {
                MenuResponse res = gson.fromJson(stored, MenuResponse.class);
                MyApplication.setData(res.data);
                Log.e(TAG_MENU,stored);
                MyApplication.getData().setCareServicesMenu(res.data.getCareServicesMenu());
                //Log.e("CARESERVICESMENU",MyApplication.getData().careServicesMenu.get(0).title);
                getToken();

            }else{

            }
        }else{
            Log.e(TAG_MENU,"NO");
            Call<MenuResponse> menus = MyApiService.getApiService().getMenu();
            menus.enqueue(new Callback<MenuResponse>() {
                @Override
                public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        MenuResponse _response = response.body();
                        String menus = gson.toJson(_response);
                        Log.e("ResponseMenu", menus);
                        MyApplication.setData(response.body().data);
                        my_preferences.savejsonString(TAG_DATA_MENU, menus);
                        my_preferences.saveElement(TAG_MENU, true);
                        getToken();

                    } else {
                        // parse the response body …
                        ResponseBody error = response.errorBody();
                        // … and use it to show error information

                        // … or just log the issue like we’re doing :)
                        Log.d("error message", error.toString());
                        Toast.makeText(SplashActivity.this, "NO SE PUEDE ACCEDER AL SERVIDOR", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<MenuResponse> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, "Esta aplicación solo funciona con conexión a internet", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void getRequestType(){

        //Comprobamos si ya existen datos
        if(my_preferences.readElement(TAG_REQUEST_TYPE,false)) {
            Log.e(TAG_REQUEST_TYPE,"YES");
            //Extraemos los datos
            Gson gson = new Gson();
            String stored = my_preferences.readjsonString(TAG_DATA_REQUEST_TYPE,null);
            if(stored!=null)
            {
                RequestTypeResponse res = gson.fromJson(stored, RequestTypeResponse.class);
                MyApplication.setTypes(res.data);
                startMainActivity();

            }else{

            }
        }else{
            Log.e(TAG_REQUEST_TYPE,"NO");

            Call<RequestTypeResponse> menus = MyApiService.getApiService().getRequestTypes();
            menus.enqueue(new Callback<RequestTypeResponse>() {
                @Override
                public void onResponse(Call<RequestTypeResponse> call, Response<RequestTypeResponse> response) {
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        RequestTypeResponse _response = response.body();
                        String menus = gson.toJson(_response);
                        Log.e("ResponseRequestType", menus);
                        MyApplication.setTypes(response.body().data);
                        my_preferences.savejsonString(TAG_DATA_REQUEST_TYPE,menus);
                        my_preferences.saveElement(TAG_REQUEST_TYPE,true);
                        startMainActivity();
                    }else {
                        // parse the response body …
                        ResponseBody error = response.errorBody();
                        // … and use it to show error information

                        // … or just log the issue like we’re doing :)
                        Log.d("error message", error.toString());
                        Toast.makeText(SplashActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<RequestTypeResponse> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, "Esta aplicación solo funciona con conexión a internet", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
    public void loadUser(){
        if(my_preferences.readElement(TAG_LOGGED,false)){
            Log.e(TAG_LOGGED,"YES");
            DataUser user = new DataUser();
            user.setIdentificacion(my_preferences.readjsonString(TAG_ID,""));
            user.setPago(my_preferences.readjsonString(TAG_PAYMENT_DATE,""));
            user.setCorreo(my_preferences.readjsonString(TAG_EMAIL,""));
            user.setTelefono(my_preferences.readjsonString(TAG_PHONE,""));
            user.setSolicitud(my_preferences.readjsonString(TAG_REQUEST,""));
            Log.e(TAG_ID,user.getIdentificacion());
            Log.e(TAG_PAYMENT_DATE,user.getPago());
            Log.e(TAG_EMAIL,user.getCorreo());
            Log.e(TAG_PHONE,user.getTelefono());
            Log.e(TAG_REQUEST,user.getSolicitud());
            MyApplication.set_dataUser(user);
            getMenus();
        }else{
            Log.e(TAG_LOGGED,"NO");
            getMenus();
        }

    }

    public void getToken(){

       /**
        if(my_preferences.readElement(TAG_TOKEN,false)){

            String stored = my_preferences.readjsonString(TAG_DATA_TOKEN,null);
            if(stored != null){
                String token = my_preferences.readjsonString(TAG_DATA_TOKEN,"");
                MyApplication.setToken(token);
                Log.e(TAG_TOKEN,token);

            }else{

            }
        }else{
            **/
            Log.e(TAG_TOKEN,"NO");
            Call<ResponseToken> token = MyApiService.getApiService().getTokenSystem("pasaporte-app","Pasaporte#2016Nx");
            token.enqueue(new Callback<ResponseToken>() {
                @Override
                public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                    if(response.isSuccessful()){
                        ResponseToken token = response.body();

                        Log.e(TAG_TOKEN,token.data.token);
                        MyApplication.setToken(token.data.token);
                        my_preferences.savejsonString(TAG_DATA_TOKEN,token.data.token);
                        my_preferences.saveElement(TAG_TOKEN,true);
                        getRequestType();
                        getTokenFirebase();
                    }else {
                        // parse the response body …
                        ResponseBody error = response.errorBody();
                        // … and use it to show error information

                        // … or just log the issue like we’re doing :)
                        Log.d("error message", error.toString());
                        Toast.makeText(SplashActivity.this, "NO SE PUEDE ACCEDER AL SERVIDOR", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseToken> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, "Esta aplicación solo funciona con conexión a internet", Toast.LENGTH_SHORT).show();

                }
            });
        //}

    }
    public void getTokenFirebase(){
        MyApplication.setTokenFirebase(FirebaseInstanceId.getInstance().getToken());
    }
    public void startMainActivity(){
        Thread timerTread = new Thread(){
            public void run(){
                try{
                    sleep(3000);

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerTread.start();
    }

}
