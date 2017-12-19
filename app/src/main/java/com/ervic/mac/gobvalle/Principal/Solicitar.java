package com.ervic.mac.gobvalle.Principal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.BottomNavigationViewHelper;
import com.ervic.mac.gobvalle.MainActivity;
import com.ervic.mac.gobvalle.PreferenciasGobvalle;
import com.ervic.mac.gobvalle.R;
import com.ervic.mac.gobvalle.WebView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EncodingUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.squareup.*;

import okhttp3.Request;
import okhttp3.Response;


public class Solicitar extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private Context context = this;
    private android.webkit.WebView web_solicitar;
    private String url = "http://qa-pidame.nexura.com/loader.php?lServicio=Pasaporte&lFuncion=createAppointment";
    private android.webkit.WebView webView;
    BottomNavigationView bottomNavigationView;
    private PreferenciasGobvalle my_preferences;
    private String TAG_LOGGED = "LOGGED";
    private String TAG_ID ="ID";
    private String TAG_EMAIL = "EMAIL";
    private String TAG_PHONE = "PHONE";
    private String TAG_PAYMENT_DATE = "DATE";
    private String TAG_REQUEST = "REQUEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar);
        my_preferences = new PreferenciasGobvalle(this);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
        ImageView btn_back = (ImageView)findViewById(R.id.btn_back);
        web_solicitar = (android.webkit.WebView) findViewById(R.id.web_solicitar);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Solicitar.this, MainActivity.class);
                startActivity(intent);
            }
        });

        isLogged();


        final Menu menu = bottomNavigationView.getMenu();
        int i = 0;

        for(i = 0; i < MyApplication.getData().bottomMenu.size(); i++) {
            final int aux = i;
            Log.e("ENTRO",MyApplication.getData().bottomMenu.get(aux).title);
            Picasso.with(this).load(MyApplication.getData().bottomMenu.get(i).icon).placeholder(getResources().getDrawable(R.drawable.ic_launcher_background)).into(new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    menu.add(Menu.NONE, aux, Menu.NONE, MyApplication.getData().bottomMenu.get(aux).title).setIcon(drawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    Log.e("ENTRO",MyApplication.getData().bottomMenu.get(aux).title);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }
        bottomNavigationView.invalidate();
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);






    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        MenuItem menuItem = bottomNavigationView.getMenu().getItem(item.getItemId()).setChecked(true);
        switch (item.getItemId()) {
            case 0: {
                Intent intent = new Intent(Solicitar.this,WebView.class);
                intent.putExtra("item",0);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(Solicitar.this,WebView.class);
                intent.putExtra("item",1);
                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(Solicitar.this,WebView.class);
                intent.putExtra("item",2);
                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(Solicitar.this,WebView.class);
                intent.putExtra("item",3);
                startActivity(intent);
            }
            break;
        }
        return false;
    }


    public void isLogged(){

        if(my_preferences.readElement(TAG_LOGGED,false)){
            WebSettings webSettings = web_solicitar.getSettings();
            webSettings.setJavaScriptEnabled(true);
            web_solicitar.setWebViewClient(getWebViewClient());
            web_solicitar.loadUrl(url,getCustomHeaders());

            Log.e("nx-bodycss", "app-design");
            Log.e("nx-user-identification", MyApplication.get_dataUser().getIdentificacion());
            Log.e("nx-user-telefono",MyApplication.get_dataUser().getTelefono());
            Log.e("nx-user-correo ",MyApplication.get_dataUser().getCorreo());
            Log.e("nx-user-tipo-solicitud",MyApplication.get_dataUser().getSolicitud());
            Log.e("nx-user-fecha-pago", MyApplication.get_dataUser().getPago());
            Log.e("nx-user-token",MyApplication.getTokenFirebase());

        }else{
            WebSettings webSettings = web_solicitar.getSettings();
            webSettings.setJavaScriptEnabled(true);
            web_solicitar.setWebViewClient(getWebViewClient());
            web_solicitar.loadUrl(url,getCustomHeaders());
            Log.e("nx-user-token",MyApplication.getTokenFirebase());

        }

    }
    private Map<String, String> getCustomHeaders()
    {
        Map<String, String> headers = new HashMap<>();
        if(my_preferences.readElement(TAG_LOGGED,false)) {
            headers.put("nx-bodycss", "app-design");
            headers.put("nx-user-identification", MyApplication.get_dataUser().getIdentificacion());
            headers.put("nx-user-telefono",MyApplication.get_dataUser().getTelefono());
            headers.put("nx-user-correo",MyApplication.get_dataUser().getCorreo());
            headers.put("nx-user-tipo-solicitud",MyApplication.get_dataUser().getSolicitud());
            headers.put("nx-user-fecha-pago", MyApplication.get_dataUser().getPago());
            headers.put("nx-user-token",MyApplication.getTokenFirebase());
            return headers;
        }else{
            headers.put("nx-bodycss", "app-design");
            headers.put("nx-user-token",MyApplication.getTokenFirebase());
            return headers;
        }
    }


    @Override
    public void onBackPressed(){

       Intent intent = new Intent(Solicitar.this,MainActivity.class);
        finish();
        startActivity(intent);

    }
    private Map<String,String> getHeader()
    {
        Map<String,String> header = new HashMap<>();
        header.put("nx-bodycss", "app-design");
        return  header;

    }
    private WebViewClient getWebViewClient()
    {

        return new WebViewClient()
        {

            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request)
            {
                view.loadUrl(request.getUrl().toString(), getHeader());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url)
            {
                view.loadUrl(url, getHeader());
                return true;
            }



        };
    }

}
