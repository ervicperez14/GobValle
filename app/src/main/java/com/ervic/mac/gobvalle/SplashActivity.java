package com.ervic.mac.gobvalle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.ervic.mac.gobvalle.API.MyApiService;
import com.ervic.mac.gobvalle.Models.DataType;
import com.ervic.mac.gobvalle.Models.MenuResponse;
import com.ervic.mac.gobvalle.Application.*;
import com.ervic.mac.gobvalle.Models.RequestTypeResponse;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private Context context = this;
    private String TAG_MENU = "MENUS";
    private String TAG_DATA_MENU = "MENUS_DATA";
    private String TAG_REQUEST_TYPE ="REQUEST_TYPE";
    private String TAG_DATA_REQUEST_TYPE ="REQUEST_TYPE_DATA";
    private PreferenciasGobvalle my_preferences;
    private MyApplication _app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        my_preferences = new PreferenciasGobvalle(this);
        _app = new MyApplication().getInstance();

        Thread timerTread = new Thread(){
            public void run(){
                try{
                    getMenus();
                    getRequestType();
                    sleep(1000);
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                } finally {


                }
            }
        };
        timerTread.start();

    }


    public void getMenus(){
        //Comprobamos si ya existen datos
        if(my_preferences.readElement(TAG_MENU,false)) {
            //Extraemos los datos
            Gson gson = new Gson();
            String stored = my_preferences.readjsonString(TAG_DATA_MENU,null);
            if(stored!=null)
            {
                MenuResponse res = gson.fromJson(stored, MenuResponse.class);
                MyApplication.setData(res.data);
            }else{

            }


        }else{


            Call<MenuResponse> menus = MyApiService.getApiService().getMenu();
            menus.enqueue(new Callback<MenuResponse>() {
                @Override
                public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                    if (response.isSuccessful()) {
                        MenuResponse menu = response.body();
                        Gson gson = new Gson();
                        MenuResponse _response = response.body();
                        String menus = gson.toJson(_response);
                        Log.e("ResponseMenu", menus);
                        MyApplication.setData(response.body().data);
                        my_preferences.savejsonString(TAG_DATA_MENU,menus);
                        my_preferences.saveElement(TAG_MENU,true);

                    }
                }

                @Override
                public void onFailure(Call<MenuResponse> call, Throwable t) {

                }
            });
        }
    }

    public void getRequestType(){

        //Comprobamos si ya existen datos
        if(my_preferences.readElement(TAG_REQUEST_TYPE,false)) {
            //Extraemos los datos
            Gson gson = new Gson();
            String stored = my_preferences.readjsonString(TAG_DATA_REQUEST_TYPE,null);
            if(stored!=null)
            {
                RequestTypeResponse res = gson.fromJson(stored, RequestTypeResponse.class);
                MyApplication.setTypes(res.data);
            }else{

            }


        }else{


            Call<RequestTypeResponse> menus = MyApiService.getApiService().getRequestTypes();
            menus.enqueue(new Callback<RequestTypeResponse>() {
                @Override
                public void onResponse(Call<RequestTypeResponse> call, Response<RequestTypeResponse> response) {
                    if (response.isSuccessful()) {
                        RequestTypeResponse menu = response.body();
                        Gson gson = new Gson();
                        DataType _response = response.body().data;
                        String menus = gson.toJson(_response);
                        Log.e("ResponseRequestType", menus);
                        MyApplication.setTypes(response.body().data);
                        my_preferences.savejsonString(TAG_DATA_REQUEST_TYPE,menus);
                        my_preferences.saveElement(TAG_REQUEST_TYPE,true);

                    }
                }

                @Override
                public void onFailure(Call<RequestTypeResponse> call, Throwable t) {

                }
            });
        }

    }

}
