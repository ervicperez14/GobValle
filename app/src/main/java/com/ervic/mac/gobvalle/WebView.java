package com.ervic.mac.gobvalle;

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
import android.webkit.WebResourceResponse;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.Principal.Pagos;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.util.Base64;

import org.apache.http.client.ClientProtocolException;

public class WebView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    ImageView btn_back;
    ProgressDialog progress;

    BottomNavigationView bottomNavigationView;
    android.webkit.WebView myWebView;
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);

        myWebView = (android.webkit.WebView) findViewById(R.id._webview);
        int item = getIntent().getIntExtra("item",0);
        String url = getIntent().getStringExtra("url");

        progress = new ProgressDialog(this);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebView.this,MainActivity.class);
                finish();
                startActivity(intent);
            }
        });



        progress.setTitle("Cargando");
        progress.setMessage("Por favor espere ...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();
        myWebView.setWebViewClient(getWebViewClient());
        myWebView.loadUrl(url,getCustomHeaders());
        myWebView.getSettings().setJavaScriptEnabled(true);
        cancelProgress();

        final Menu menu = bottomNavigationView.getMenu();
        int i = 0;

        for(i = 0;i < MyApplication.getData().bottomMenu.size();i++) {
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
                    //menu.add(Menu.NONE, aux, Menu.NONE, MyApplication.getData().bottomMenu.get(aux).title).setIcon(placeHolderDrawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    //Log.e("ENTRO",MyApplication.getData().bottomMenu.get(aux).title);
                }
            });
        }
        bottomNavigationView.invalidate();
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(item).setChecked(true);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        int mMenuId = item.getItemId();
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
                MenuItem menuItem = bottomNavigationView.getMenu().getItem(item.getItemId()).setChecked(true);

        }

        switch (item.getItemId()) {
            case 0: {
                String url = MyApplication.getData().bottomMenu.get(0).link;
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.loadUrl(url,getCustomHeaders());
            }
            break;
            case 1: {
                String url = MyApplication.getData().bottomMenu.get(1).link;
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.loadUrl(url,getCustomHeaders());
            }
            break;
            case 2: {
                String url = MyApplication.getData().bottomMenu.get(2).link;
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.loadUrl(url,getCustomHeaders());
            }
            break;
            case 3: {
                String url = MyApplication.getData().bottomMenu.get(3).link;
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.loadUrl(url,getCustomHeaders());

            }
            break;
        }
        return false;
    }

    public void cancelProgress(){
        Thread timerTread = new Thread(){
            public void run(){
                try{


                    sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                } finally {

                    progress.cancel();
                }
            }
        };
        timerTread.start();
    }
    private Map<String, String> getCustomHeaders()
    {
        Map<String, String> headers = new HashMap<>();
            headers.put("nx-bodycss", "app-design");
            return headers;

    }
    @Override
    public void onBackPressed(){

        Intent intent = new Intent(WebView.this,MainActivity.class);
        finish();
        startActivity(intent);
    }
    private WebViewClient getWebViewClient()
    {

        return new WebViewClient()
        {

            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request)
            {
                view.loadUrl(request.getUrl().toString(), getCustomHeaders());

                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url)
            {
                view.loadUrl(url, getCustomHeaders());
                return true;
            }
        };
    }

}
