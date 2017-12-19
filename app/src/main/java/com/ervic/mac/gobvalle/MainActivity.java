package com.ervic.mac.gobvalle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ervic.mac.gobvalle.Adapters.AdapterInformacion;
import com.ervic.mac.gobvalle.Adapters.AdaptercareServices;
import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.DataBase.AdminOpenHelper;
import com.ervic.mac.gobvalle.DataBase.Constantes;
import com.ervic.mac.gobvalle.Models.DataUser;
import com.ervic.mac.gobvalle.Models.leftMenu;
import com.ervic.mac.gobvalle.Principal.Cancelar;
import com.ervic.mac.gobvalle.Principal.Consultar;
import com.ervic.mac.gobvalle.Principal.DatePickerFragment;
import com.ervic.mac.gobvalle.Principal.Pagos;
import com.ervic.mac.gobvalle.Principal.Registrarse;
import com.ervic.mac.gobvalle.Principal.Reprogramar;
import com.ervic.mac.gobvalle.Principal.Solicitar;
import com.ervic.mac.gobvalle.Models.careServicesMenu;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private String tokenFirebaseNotification;
    final int callbackId = 42;
    private String TAG_ID ="ID";
    private String TAG_EMAIL = "EMAIL";
    private String TAG_PHONE = "PHONE";
    private String TAG_PAYMENT_DATE = "DATE";
    private String TAG_REQUEST = "REQUEST";
    private AdminOpenHelper _adminDB;
    private SQLiteDatabase _database;
    private View layout_perfil, layout_login;
    private TextView tv_identificacion, tv_fecha_pago,tv_telefono, tv_correo;
    private TextView login_identificacion, login_fecha_pago;
    private LinearLayout container_registrarse, container_realizarpago;
    private TextInputLayout  container_button_login;
    private String TAG_LOGGED = "LOGGED";
    private ImageButton __registrarse, __pago, __solicitar, __consultar,__cancelar,__reprogramar;
    private PreferenciasGobvalle my_preferences;
    private MyApplication _app = MyApplication.getInstance();
    BottomNavigationView bottomNavigationView;
    private Animation animation;
    private ImageView btn_opcion;
    private LinearLayout ly_opcion, ly_btn_back;
    private LinearLayout container_recycler, container_recycler_scareservices;
    private RecyclerView mRecycler,mRecycler_care;
    private AdapterInformacion mAdapter;
    private AdaptercareServices mAdapter_care;
    private RecyclerView.LayoutManager mLayoutManager, mlayoutManager_care;
    private Bitmap theBitmap = null;
    private List<careServicesMenu>  lista_scare = null;
    private List<leftMenu> lista = null;
    private List<com.squareup.picasso.Target> targets = new ArrayList<com.squareup.picasso.Target>();
    private ImageView btn_back;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_opcion = (ImageView)  findViewById(R.id.opcion);
        ly_opcion = (LinearLayout) findViewById(R.id.ly_opcion);
        ly_btn_back = (LinearLayout) findViewById(R.id.ly_btn_back);
        container_recycler = (LinearLayout) findViewById(R.id.container_recycler);
        container_recycler_scareservices = (LinearLayout) findViewById(R.id.container_scareservices);
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.app_bar);
        PopupMenu popup = new PopupMenu(appBar.getContext(), toolbar);
       bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
       mRecycler_care = (RecyclerView) findViewById(R.id.recycler_scare);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_menulateral);
        container_realizarpago = (LinearLayout) findViewById(R.id.container_realizarpago);
        container_registrarse = (LinearLayout) findViewById(R.id.container_registro);
        layout_perfil = findViewById(R.id.layout_perfil);
        layout_login = findViewById(R.id.layout_login);
        _adminDB = new AdminOpenHelper(getApplicationContext());
        _database = _adminDB.getWritableDatabase();
        btn_back = (ImageView) findViewById(R.id.btn_back);
        //Include perfil
        tv_identificacion = (TextView) layout_perfil.findViewById(R.id.perfil_identificacion);
        tv_fecha_pago = (TextView) layout_perfil.findViewById(R.id.perfil_fecha_pago);
        tv_telefono = (TextView) layout_perfil.findViewById(R.id.perfil_telefono);
        tv_correo = (TextView) layout_perfil.findViewById(R.id.perfil_correo);
        //Include login
        login_identificacion = (TextView)layout_login.findViewById(R.id.login_numid);
        login_fecha_pago = (TextView)layout_login.findViewById(R.id.login_fechapago);
        my_preferences = new PreferenciasGobvalle(this);
        mRecycler.setHasFixedSize(true);
        mRecycler_care.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mlayoutManager_care = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler_care.setLayoutManager(mlayoutManager_care);
        lista_scare = _app.getData().careServicesMenu;
        lista = new ArrayList<leftMenu>();
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        if(my_preferences.readElement(TAG_LOGGED,false)){
            container_registrarse.setVisibility(View.GONE);
            container_realizarpago.setVisibility(View.GONE);

            leftMenu perfil = new leftMenu("Perfil","","perfil");
            lista.add(perfil);

        }else{
            leftMenu sesion = new leftMenu("Iniciar Sesión","","sesion");
            lista.add(sesion);
        }
        leftMenu atencion = new leftMenu("Servicios de Atención","","atencion");
        lista.add(atencion);
        lista.addAll(MyApplication.getData().leftMenu);
        if(my_preferences.readElement(TAG_LOGGED,false)) {
            leftMenu cerrar_sesion = new leftMenu("Cerrar Sesión", "", "cerrar");
            lista.add(cerrar_sesion);
        }
        mAdapter = new AdapterInformacion(this,lista);
        mAdapter_care = new AdaptercareServices(this,lista_scare);
        mRecycler.setAdapter(mAdapter);
        mRecycler_care.setAdapter(mAdapter_care);



        final Menu menu = bottomNavigationView.getMenu();

        bottomNavigationView.invalidate();
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);

        for(i = 0; i <MyApplication.getData().bottomMenu.size();i++) {
            final int k = i;

                    Drawable drawablep = getResources().getDrawable( R.drawable.reload );
                    menu.add(Menu.NONE, i, Menu.NONE, MyApplication.getData().bottomMenu.get(i).title).setIcon(drawablep).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                        com.squareup.picasso.Target target = new com.squareup.picasso.Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            targets.remove(this);

                                menu.findItem(k).setIcon(drawable);
                            //menu.add(Menu.NONE, i, Menu.NONE, MyApplication.getData().bottomMenu.get(k).title).setIcon(drawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                            BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

                        }
                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            targets.remove(this);
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            Log.i("Targets", "Preparing: " + k);
                            menu.findItem(k).setIcon(placeHolderDrawable);
                            //menu.add(Menu.NONE, i, Menu.NONE, MyApplication.getData().bottomMenu.get(k).title).setIcon(drawable).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                            if(menu.findItem(i)!= null)
                                menu.findItem(i).setIcon(placeHolderDrawable);
                        }
                    };
                        targets.add(target);
            Picasso.with(MainActivity.this)
                    .load(MyApplication.getData().bottomMenu.get(i).icon)
                    .placeholder(getResources().getDrawable(R.drawable.reload))
                    .into(target);

                }






        btn_opcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(container_recycler.getVisibility() == View.GONE){
                    showleftMenu();
                    showArrorBack();
                    hideBtnleftMenu();
                }else if(container_recycler.getVisibility() == View.VISIBLE){
                    hideleftMenu();
                    hidelayoutPerfil();
                    showBtnleftMenu();
                }
            }
        });
        ly_opcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(container_recycler.getVisibility() == View.GONE){
                    showleftMenu();
                    showArrorBack();
                    hideBtnleftMenu();
                }else if(container_recycler.getVisibility() == View.VISIBLE){
                    hideleftMenu();
                    hidelayoutPerfil();
                    showBtnleftMenu();
                }
            }
        });

        final GestureDetector mGestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

    mRecycler_care.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if(child != null && mGestureDetector.onTouchEvent(e)){
                int position = rv.getChildAdapterPosition(child);

                Intent intent = new Intent(MainActivity.this, WebViewCare.class);
                intent.putExtra("url",MyApplication.getData().careServicesMenu.get(position).link);
                startActivity(intent);

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    });

    mRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        int position = recyclerView.getChildAdapterPosition(child);

                        //Toast.makeText(MainActivity.this,"The Item Clicked is: "+ position ,Toast.LENGTH_SHORT).show();

                            if(position == 0){
                                if(my_preferences.readElement(TAG_LOGGED,false)){
                                    Log.e("ENTRA",String.valueOf(position));
                                    tv_identificacion.setText(Html.fromHtml("<b>Identificación:</b> " + MyApplication.get_dataUser().getIdentificacion()));
                                    tv_correo.setText(Html.fromHtml("<b>Correo:</b> "+ MyApplication.get_dataUser().getCorreo()));
                                    tv_fecha_pago.setText(Html.fromHtml("<b>Fecha de pago:</b> "+ MyApplication.get_dataUser().getPago()));
                                    tv_telefono.setText(Html.fromHtml("<b>Teléfono:</b> "+MyApplication.get_dataUser().getTelefono()));
                                    showlayoutPerfil();
                                }else{
                                    Log.e("OPEN LOGIN","TRUE");
                                    openLogin();

                                }
                            }else if(position == 1){
                                if(container_recycler_scareservices.getVisibility() == View.GONE)
                                    showcareServicesMenu();
                                else{
                                    hidecareServicesMenu();
                                }

                            }else if(position == (MyApplication.getData().leftMenu.size()+2)){
                                my_preferences.saveElement(TAG_LOGGED,false);
                                restartActivity();

                            }else{
                                Intent intent = new Intent(MainActivity.this, WebViewCare.class);
                                intent.putExtra("url",MyApplication.getData().leftMenu.get(position-2).link);
                                startActivity(intent);
                            }



                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

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

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(container_recycler_scareservices.getVisibility() == View.VISIBLE){
                    hidecareServicesMenu();
                }else if(layout_perfil.getVisibility() == View.VISIBLE){
                    hidelayoutPerfil();

                }else if(container_recycler.getVisibility() == View.VISIBLE) {

                    hideleftMenu();
                    showBtnleftMenu();
                    hideArrorBack();
                }
            }
        });
        /**
     if(MyApplication.restarActivity)
         restartActivity();
         **/
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
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(0).link);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(MainActivity.this,WebView.class);
                intent.putExtra("item",1);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(1).link);

                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(MainActivity.this,WebView.class);
                intent.putExtra("item",2);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(2).link);

                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(MainActivity.this,WebView.class);
                intent.putExtra("item",3);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(3).link);

                startActivity(intent);
            }
            break;
            case 4: {
                Intent intent = new Intent(MainActivity.this,WebView.class);
                intent.putExtra("item",4);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(4).link);

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

    public void showcareServicesMenu(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mostrar_care);
        container_recycler_scareservices.startAnimation(animation);
        container_recycler_scareservices.setVisibility(View.VISIBLE);

    }
    public void hidecareServicesMenu(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ocultar_care);
        container_recycler_scareservices.startAnimation(animation);
        container_recycler_scareservices.setVisibility(View.GONE);
    }

    public void showlayoutPerfil(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mostrar);
        layout_perfil.startAnimation(animation);
        layout_perfil.setVisibility(View.VISIBLE);
    }
    public void hidelayoutPerfil(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ocultar);
        layout_perfil.startAnimation(animation);
        layout_perfil.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed(){

        if(container_recycler_scareservices.getVisibility() == View.VISIBLE){
            hidecareServicesMenu();


        }else if(layout_perfil.getVisibility() == View.VISIBLE){
            hidelayoutPerfil();

        }else if(container_recycler.getVisibility() == View.VISIBLE){
            hideleftMenu();

            showBtnleftMenu();
            hideArrorBack();
            //btn_opcion.setVisibility(View.VISIBLE);
            //ly_opcion.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 200){


        }

    }
    private void showDatePickerDialog(final EditText fecha) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                fecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }
    private boolean isValid(String cadena){
        int textLength = cadena.length();
        return textLength > 0;
    }

    public void openLogin(){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.content_login, null);

        final EditText identificacion = (EditText)v.findViewById(R.id.login_numid);
        final EditText fecha_pago = (EditText)v.findViewById(R.id.login_fechapago);
        final TextInputLayout containerid = (TextInputLayout)v.findViewById(R.id.container_numid_login);
        final TextInputLayout containerfechapago = (TextInputLayout)v.findViewById(R.id.container_fechapago_login);
        container_button_login = (TextInputLayout) v.findViewById(R.id.container_button_login);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        // this is set the view from XML inside AlertDialog
        alert.setView(v);
        final AlertDialog dialog = alert.create();
        dialog.show();
        final ProgressDialog progress_enviar = new ProgressDialog(this);
        Button btn_login = (Button) v.findViewById(R.id.btn_login);

        fecha_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(fecha_pago);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progress_enviar.show();
                boolean numeroError = isValid(identificacion.getText().toString());
                boolean fechaError = isValid(fecha_pago.getText().toString());
                containerid.setError(numeroError ? "Campo obligatorio": "Campo obligatorio");
                containerfechapago.setError(fechaError ? "Selecciona la fecha de pago": "Selecciona la fecha de pago");
                containerid.setErrorEnabled(!numeroError);
                containerfechapago.setErrorEnabled(!fechaError);
                if(numeroError && fechaError){
                    progress_enviar.show();
                    containerfechapago.setErrorEnabled(false);
                    containerid.setErrorEnabled(false);

                    Cursor fila = _database.rawQuery(
                            "select * from " + Constantes.Tabla_usuario.TABLA_USUARIO+ " where "+ Constantes.Tabla_usuario.COLUMNA_NUMERO_IDENTIFICACION +" = '"+ identificacion.getText().toString() +"' and "+ Constantes.Tabla_usuario.COLUMNA_FECHA_PAGO+ " = '"+fecha_pago.getText().toString()+"'", null);
                    int numRows = (int) DatabaseUtils.longForQuery(_database, "SELECT COUNT(*) FROM usuario"+ " where "+ Constantes.Tabla_usuario.COLUMNA_NUMERO_IDENTIFICACION +" = '"+ identificacion.getText().toString() +"' and "+ Constantes.Tabla_usuario.COLUMNA_FECHA_PAGO+ " = '"+fecha_pago.getText().toString()+"'", null);
                    Log.e("NUMERO",String.valueOf(numRows));

                    if(fila != null && numRows >0){
                        fila.moveToFirst();
                        my_preferences.saveElement(TAG_LOGGED,true);
                        my_preferences.savejsonString(TAG_ID, fila.getString(1));
                        my_preferences.savejsonString(TAG_PAYMENT_DATE, fila.getString(2));
                        my_preferences.savejsonString(TAG_EMAIL,fila.getString(5));
                        my_preferences.savejsonString(TAG_PHONE,fila.getString(4));
                        my_preferences.savejsonString(TAG_REQUEST,fila.getString(3));
                        DataUser user = new DataUser();
                        user.setIdentificacion(fila.getString(1));
                        user.setPago(fila.getString(2));
                        user.setSolicitud(fila.getString(3));
                        user.setTelefono(fila.getString(4));
                        user.setCorreo(fila.getString(5));
                        MyApplication.set_dataUser(user);
                        Log.e("IDENTIFICACION",MyApplication.get_dataUser().getIdentificacion());
                        Log.e("FECHA DE PAGO",MyApplication.get_dataUser().getPago());
                        Log.e("TIPO DE SOLICITUD",MyApplication.get_dataUser().getSolicitud());
                        Log.e("TELEFONO",MyApplication.get_dataUser().getTelefono());
                        Log.e("CORREO",MyApplication.get_dataUser().getCorreo());
                        Toast.makeText(MainActivity.this,"Has iniciado sesión correctamente",Toast.LENGTH_LONG);
                        progress_enviar.dismiss();
                        dialog.dismiss();
                        restartActivity();

                    }else{

                                    //container_button_login.setError(true ? "Datos incorrectos": "Datos incorrectos");
                                    Log.e("USUARIO","no encontrado");
                                    //Toast.makeText(MainActivity.this,"Datos incorrectos",Toast.LENGTH_SHORT);
                            Snackbar.make(findViewById(R.id.layout_login),"Datos incorrectos",Snackbar.LENGTH_LONG).show();

                                    progress_enviar.dismiss();


                    }

                }else{
                    progress_enviar.dismiss();
                }
            }
        });



    }
    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
        MyApplication.restarActivity = false;

    }

    private void checkPermissions(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
    }
    public static Drawable createDrawableFromUrl(String imageWebAddress)
    {
        Drawable drawable = null;

        try
        {
            InputStream inputStream = new URL(imageWebAddress).openStream();
            drawable = Drawable.createFromStream(inputStream, "src");
            inputStream.close();
        }
        catch (MalformedURLException ex) { }
        catch (IOException ex) { }
        catch (NetworkOnMainThreadException ex){}

        return drawable;
    }

    private void showArrorBack(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        btn_back.startAnimation(animation);
        btn_back.setVisibility(View.VISIBLE);
        ly_btn_back.setVisibility(View.VISIBLE);
    }
    private void hideArrorBack(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        btn_back.startAnimation(animation);
        btn_back.setVisibility(View.GONE);
        ly_btn_back.setVisibility(View.GONE);
    }
    private  void showBtnleftMenu(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        btn_opcion.startAnimation(animation);
        btn_opcion.setVisibility(View.VISIBLE);
        ly_opcion.setVisibility(View.VISIBLE);
    }
    private  void hideBtnleftMenu(){
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        btn_opcion.startAnimation(animation);
        btn_opcion.setVisibility(View.GONE);
        ly_opcion.setVisibility(View.GONE);
    }


}
