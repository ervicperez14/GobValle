package com.ervic.mac.gobvalle.Principal;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
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
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.BottomNavigationViewHelper;
import com.ervic.mac.gobvalle.MainActivity;
import com.ervic.mac.gobvalle.PreferenciasGobvalle;
import com.ervic.mac.gobvalle.R;
import com.ervic.mac.gobvalle.WebView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Reprogramar extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    ProgressDialog progress;
    private android.webkit.WebView web_reprogramar;
    BottomNavigationView bottomNavigationView;
    private String TAG_LOGGED = "LOGGED";
    private PreferenciasGobvalle my_preferences;
    private String url = "http://qa-pidame.nexura.com/loader.php?lServicio=Pasaporte&lFuncion=consult&tipo=5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprogramar);
        web_reprogramar = (android.webkit.WebView) findViewById(R.id.web_reprogramar);
        my_preferences = new PreferenciasGobvalle(this);
        progress = new ProgressDialog(this);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
        ImageView btn_back = (ImageView)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reprogramar.this, MainActivity.class);
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
                    //menu.add(Menu.NONE, aux, Menu.NONE, MyApplication.getData().bottomMenu.get(aux).title).setIcon(placeHolderDrawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    //Log.e("ENTRO",MyApplication.getData().bottomMenu.get(aux).title);
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
                Intent intent = new Intent(Reprogramar.this,WebView.class);
                intent.putExtra("item",0);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(Reprogramar.this,WebView.class);
                intent.putExtra("item",1);
                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(Reprogramar.this,WebView.class);
                intent.putExtra("item",2);
                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(Reprogramar.this,WebView.class);
                intent.putExtra("item",3);
                startActivity(intent);
            }
            break;
        }
        return false;
    }


    public void cancelProgress(){
        Thread timerTread = new Thread(){
            public void run(){
                try{


                    sleep(3000);
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
    public void isLogged(){

        if(my_preferences.readElement(TAG_LOGGED,false)){
            WebSettings webSettings = web_reprogramar.getSettings();
            webSettings.setJavaScriptEnabled(true);
            web_reprogramar.setWebViewClient(getWebViewClient());
            web_reprogramar.loadUrl(url,getCustomHeaders());
            cancelProgress();




        }else{
            WebSettings webSettings = web_reprogramar.getSettings();
            webSettings.setJavaScriptEnabled(true);
            web_reprogramar.setWebViewClient(getWebViewClient());
            web_reprogramar.loadUrl(url,getCustomHeaders());
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

        Intent intent = new Intent(Reprogramar.this,MainActivity.class);
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
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, WebResourceRequest request)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString(), getHeader());
                }
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
