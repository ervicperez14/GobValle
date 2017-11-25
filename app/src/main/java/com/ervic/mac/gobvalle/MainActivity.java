package com.ervic.mac.gobvalle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ervic.mac.gobvalle.Adapters.AdapterInformacion;
import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.Models.leftMenu;
import com.ervic.mac.gobvalle.Principal.Cancelar;
import com.ervic.mac.gobvalle.Principal.Consultar;
import com.ervic.mac.gobvalle.Principal.Pagos;
import com.ervic.mac.gobvalle.Principal.Registrarse;
import com.ervic.mac.gobvalle.Principal.Reprogramar;
import com.ervic.mac.gobvalle.Principal.Solicitar;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private ImageButton __registrarse, __pago, __solicitar, __consultar,__cancelar,__reprogramar;
    BottomNavigationView bottomNavigationView;
    private Animation animation;
    private ImageView btn_opcion;
    private LinearLayout container_recycler;
    private RecyclerView mRecycler;
    private AdapterInformacion mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_opcion = (ImageView)  findViewById(R.id.opcion);
        container_recycler = (LinearLayout) findViewById(R.id.container_recycler);
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.app_bar);
        PopupMenu popup = new PopupMenu(appBar.getContext(), toolbar);
       bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_menulateral);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        List<leftMenu> lista = new ArrayList<leftMenu>();
        leftMenu perfil = new leftMenu("Perfil","","perfil");
        leftMenu atencion = new leftMenu("Servicios de Atención","","atencion");
        lista.add(perfil);
        lista.add(atencion);
        mAdapter = new AdapterInformacion(this,lista);
        mRecycler.setAdapter(mAdapter);
        final Menu menu = bottomNavigationView.getMenu();
        int i = 0;

        for(i = 0;i < MyApplication.getData().bottomMenu.size();i++) {
            final int aux = i;
            Log.e("ENTRO",MyApplication.getData().bottomMenu.get(aux).title);
            Picasso.with(this).load(getResources().getString(R.string.server) + MyApplication.getData().bottomMenu.get(i).icon).placeholder(getResources().getDrawable(R.drawable.ic_launcher_background)).into(new com.squareup.picasso.Target() {
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
                    menu.add(Menu.NONE, aux, Menu.NONE, MyApplication.getData().bottomMenu.get(aux).title).setIcon(placeHolderDrawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    Log.e("ENTRO",MyApplication.getData().bottomMenu.get(aux).title);
                }
            });
        }
        bottomNavigationView.invalidate();
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        btn_opcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(container_recycler.getVisibility() == View.GONE){
                    showleftMenu();
                }else if(container_recycler.getVisibility() == View.VISIBLE){
                    hideleftMenu();
                }
            }
        });
        //Secciones
        __pago = (ImageButton) findViewById(R.id.pagos);
        __registrarse = (ImageButton) findViewById(R.id.registrarse);
        __solicitar = (ImageButton) findViewById(R.id.solicitar);
        __consultar = (ImageButton) findViewById(R.id.consultar);
        __cancelar = (ImageButton) findViewById(R.id.cancelar);
        __reprogramar = (ImageButton) findViewById(R.id.reprogramar);

        __pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pagos.class);
                startActivity(intent);
            }
        });

        __registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registrarse.class);
                startActivity(intent);
            }
        });

        __solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Solicitar.class);
                startActivity(intent);
            }
        });

        __consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Consultar.class);
                startActivity(intent);
            }
        });

        __cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cancelar.class);
                startActivity(intent);
            }
        });

        __reprogramar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Reprogramar.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "Click on "+ "elemento 1", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "Click on "+ "elemento 2", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "Click on "+ "elemento 3", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "Click on "+ "elemento 4", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(item.getItemId()).setChecked(true);
        switch (item.getItemId()) {
            case 0: {
                Intent intent = new Intent(MainActivity.this,WebView.class);
                intent.putExtra("item",0);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(MainActivity.this,WebView.class);
                intent.putExtra("item",1);
                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(MainActivity.this,WebView.class);
                intent.putExtra("item",2);
                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(MainActivity.this,WebView.class);
                intent.putExtra("item",3);
                startActivity(intent);
            }
            break;
        }
        return false;
    }

    public void showleftMenu(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.mostrar);
        container_recycler.startAnimation(animation);
        container_recycler.setVisibility(View.VISIBLE);
    }
    public void hideleftMenu(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ocultar);
        container_recycler.startAnimation(animation);
        container_recycler.setVisibility(View.GONE);
    }

}
