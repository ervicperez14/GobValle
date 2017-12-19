package com.ervic.mac.gobvalle.Principal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.BottomNavigationViewHelper;
import com.ervic.mac.gobvalle.MainActivity;
import com.ervic.mac.gobvalle.R;
import com.squareup.picasso.Picasso;


public class Pagos extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pago);
        ImageView btn_back = (ImageView)findViewById(R.id.btn_back);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pagos.this, MainActivity.class);
                startActivity(intent);
            }
        });
        WebView webViewPago = (WebView)findViewById(R.id._webview);
        webViewPago.getSettings().setJavaScriptEnabled(true);
        webViewPago.loadUrl("https://www.abcpagos.com/pasaportes_valle/");

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
                Intent intent = new Intent(Pagos.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",0);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(Pagos.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",1);
                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(Pagos.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",2);
                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(Pagos.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",3);
                startActivity(intent);
            }
            break;
        }
        return false;
    }



    @Override
    public void onBackPressed(){

        Intent intent = new Intent(Pagos.this,MainActivity.class);
        finish();
        startActivity(intent);
    }

}
