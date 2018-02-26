package com.ervic.mac.gobvalle.Principal;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.BottomNavigationViewHelper;
import com.ervic.mac.gobvalle.MainActivity;
import com.ervic.mac.gobvalle.PreferenciasGobvalle;
import com.ervic.mac.gobvalle.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ConsultarPago extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ImageView btn_back;
    private ProgressDialog progress;
    private BottomNavigationView bottomNavigationView;
    private android.webkit.WebView myWebView;
    private String TAG_LOGGED = "LOGGED";
    private PreferenciasGobvalle my_preferences;
    private String url = "https://pasaportes.valledelcauca.gov.co/loader.php?lServicio=Pasaporte&lFuncion=consult&tipo=3";
    private WebView web_pagos;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_pago);
        web_pagos = (android.webkit.WebView) findViewById(R.id.web_pagos);
        progress = new ProgressDialog(this);
        my_preferences = new PreferenciasGobvalle(this);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsultarPago.this, Consultar.class);
                startActivity(intent);
                finish();
            }
        });
        progress.setTitle("Cargando");
        progress.setMessage("Por favor espere ...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();
        isLogged();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
        final Menu menu = bottomNavigationView.getMenu();
        int i = 0;

        for (i = 0; i < MyApplication.getData().bottomMenu.size(); i++) {
            final int aux = i;
            Log.e("ENTRO", MyApplication.getData().bottomMenu.get(aux).title);
            Picasso.with(this).load(MyApplication.getData().bottomMenu.get(i).icon).placeholder(getResources().getDrawable(R.drawable.ic_launcher_background)).into(new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    menu.add(Menu.NONE, aux, Menu.NONE, MyApplication.getData().bottomMenu.get(aux).title).setIcon(drawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    Log.e("ENTRO", MyApplication.getData().bottomMenu.get(aux).title);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    //menu.add(Menu.NONE, aux, Menu.NONE, MyApplication.getData().bottomMenu.get(aux).title).setIcon(placeHolderDrawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    //Log.e("ENTRO", MyApplication.getData().bottomMenu.get(aux).title);
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
                Intent intent = new Intent(ConsultarPago.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item", 0);
                intent.putExtra("url", MyApplication.getData().bottomMenu.get(0).link);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(ConsultarPago.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item", 1);
                intent.putExtra("url", MyApplication.getData().bottomMenu.get(1).link);

                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(ConsultarPago.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item", 2);
                intent.putExtra("url", MyApplication.getData().bottomMenu.get(2).link);

                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(ConsultarPago.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item", 3);
                intent.putExtra("url", MyApplication.getData().bottomMenu.get(3).link);

                startActivity(intent);
            }
            break;
            case 4: {
                Intent intent = new Intent(ConsultarPago.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item", 4);
                intent.putExtra("url", MyApplication.getData().bottomMenu.get(4).link);

                startActivity(intent);
            }
            break;

        }
        return false;
    }

    public void cancelProgress() {
        Thread timerTread = new Thread() {
            public void run() {
                try {


                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    progress.cancel();
                }
            }
        };
        timerTread.start();
    }

    public void isLogged() {

        if (my_preferences.readElement(TAG_LOGGED, false)) {
            WebSettings webSettings = web_pagos.getSettings();
            webSettings.setJavaScriptEnabled(true);
            web_pagos.setWebViewClient(getWebViewClient());
            web_pagos.loadUrl(url, getCustomHeaders());
            cancelProgress();


        } else {
            WebSettings webSettings = web_pagos.getSettings();
            webSettings.setJavaScriptEnabled(true);
            web_pagos.setWebViewClient(getWebViewClient());
            web_pagos.loadUrl(url,getCustomHeaders());
            cancelProgress();

        }

    }
    private Map<String, String> getCustomHeaders()
    {
        Map<String, String> headers = new HashMap<>();
        if(my_preferences.readElement(TAG_LOGGED,false)) {

            headers.put("nx-bodycss", "app-design");
            headers.put("nx-user-identification", MyApplication.get_dataUser().getIdentificacion());
            headers.put("nx-user-fecha-pago", MyApplication.get_dataUser().getPago());
            headers.put("nx-user-token",MyApplication.getTokenFirebase());
            return headers;
        }else{
            headers.put("nx-bodycss", "app-design");



            return headers;
        }
    }
    @Override
    public void onBackPressed(){

        finish();
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

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("ConsultaPago",url);
                super.onPageStarted(view, url, favicon);
            }
        };
    }
}