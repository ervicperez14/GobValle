package com.ervic.mac.gobvalle.Principal;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ervic.mac.gobvalle.API.MyApiService;
import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.BottomNavigationViewHelper;
import com.ervic.mac.gobvalle.MainActivity;
import com.ervic.mac.gobvalle.Models.ResponseCancel;
import com.ervic.mac.gobvalle.Models.ResponseProcess;
import com.ervic.mac.gobvalle.PreferenciasGobvalle;
import com.ervic.mac.gobvalle.R;
import com.ervic.mac.gobvalle.WebView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cancelar extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    String prueba = "prueba";
    ImageView btn_back;
    ProgressDialog progress;
    BottomNavigationView bottomNavigationView;
    private String TAG_LOGGED = "LOGGED";
    private PreferenciasGobvalle my_preferences;
    private EditText et_identificacion, et_fecha_pago, et_descripcion_cancelar;
    private TextInputLayout content_numero_identificacion, content_fecha_pago;
    private Button btn_consultar;
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar);
        my_preferences = new PreferenciasGobvalle(this);
        et_identificacion = (EditText) findViewById(R.id.numero_identificacion);
        et_fecha_pago = (EditText) findViewById(R.id.fecha_pago);
        btn_consultar = (Button) findViewById(R.id.btn_consultar);
        content_numero_identificacion = (TextInputLayout) findViewById(R.id.container_numero_identificacion);
        content_fecha_pago = (TextInputLayout) findViewById(R.id.container_fecha_pago);
        progress = new ProgressDialog(this);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cancelar.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        progress.setTitle("Cargando");
        progress.setMessage("Por favor espere ...");
        progress.setCancelable(false);
        progress.setIndeterminate(true);
        progress.show();
        et_fecha_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(et_fecha_pago);
            }
        });
        isLogged();
        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                enviar();

            }
        });
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationview);
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
                Intent intent = new Intent(Cancelar.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",0);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(0).link);
                startActivity(intent);
            }
            break;
            case 1: {
                Intent intent = new Intent(Cancelar.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",1);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(1).link);

                startActivity(intent);
            }
            break;
            case 2: {
                Intent intent = new Intent(Cancelar.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",2);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(2).link);

                startActivity(intent);
            }
            break;
            case 3: {
                Intent intent = new Intent(Cancelar.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",3);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(3).link);

                startActivity(intent);
            }
            break;
            case 4: {
                Intent intent = new Intent(Cancelar.this, com.ervic.mac.gobvalle.WebView.class);
                intent.putExtra("item",4);
                intent.putExtra("url",MyApplication.getData().bottomMenu.get(4).link);

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
            et_fecha_pago.setText(MyApplication.get_dataUser().getPago());
            et_identificacion.setText(MyApplication.get_dataUser().getIdentificacion());
            Call<ResponseProcess> consulta = MyApiService.getApiService().setOptionPasaporte(MyApplication.get_dataUser().getPago(),MyApplication.get_dataUser().getIdentificacion(),"1",MyApplication.getToken());
            consulta.enqueue(new Callback<ResponseProcess>() {
                @Override
                public void onResponse(Call<ResponseProcess> call, retrofit2.Response<ResponseProcess> response) {
                    prueba = response.message();
                    if(response.isSuccessful()) {
                        cancelProgress();
                        ResponseProcess datos = response.body();
                        showMessage(datos.data.user_nombre, datos.data.user_numero_identificacion,datos.data.user_correo_electronico,datos.data.user_telefono,datos.data.fecha_cita,datos.data.hora_cita,datos.data.tipo_cita,datos.data.tipo_solicitud,datos.data.estado, datos.data.passport_estado, datos.data.id_cita, datos.data.validacionCancel);

                    }else {
                        cancelProgress();
                    }


                }

                @Override
                public void onFailure(Call<ResponseProcess> call, Throwable t) {
                    cancelProgress();
                    Log.e("Error",t.getLocalizedMessage().toLowerCase());

                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.content_formulariored, null);
                    TextView texto_mensaje = (TextView) v.findViewById(R.id.texto_mensaje);
                    Button btn_aceptar = (Button) v.findViewById(R.id.btn_aceptar);
                    texto_mensaje.setText("La información ingresada no coincide con ningún registro de cita agendada.");
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Cancelar.this);
                    // this is set the view from XML inside AlertDialog
                    alert.setView(v);
                    final AlertDialog dialog = alert.create();
                    dialog.show();
                    btn_aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
            });
        }else{
            cancelProgress();

        }

    }

    @Override
    public void onBackPressed(){

        finish();

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
    public void showMessage(String nombre, String cedula, String email, String telefono, String fecha, String hora, String tipo_cita, String tipo_solicitud, String estado, String estado_pasaporte, final String id_cita, final boolean validacionCancel){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.content_respuesta_cancelar, null);
        TextView respuesta_nombre = (TextView) v.findViewById(R.id.respuesta_nombre);
        TextView respuesta_cedula = (TextView) v.findViewById(R.id.respuesta_cedula);
        TextView respuesta_email = (TextView) v.findViewById(R.id.respuesta_email);
        TextView respuesta_telefono = (TextView) v.findViewById(R.id.respuesta_telefono);
        TextView respuesta_fecha = (TextView) v.findViewById(R.id.respuesta_fecha);
        TextView respuesta_hora = (TextView) v.findViewById(R.id.respuesta_hora);
        TextView respuesta_tipo_cita = (TextView) v.findViewById(R.id.respuesta_tipo_cita);
        TextView respuesta_tipo_solicitud = (TextView) v.findViewById(R.id.respuesta_tipo_solicitud);
        TextView respuesta_estado = (TextView) v.findViewById(R.id.respuesta_estado);
        TextView respuesta_estado_pasaporte = (TextView) v.findViewById(R.id.respuesta_estado_pasaporte);
        TextView respuesta_validacionCancel = (TextView) v.findViewById(R.id.respuesta_validacionCancel);
        Button btn_cerrar_up = (Button) v.findViewById(R.id.btn_cerrar_up);
        Button btn_cerrar_down = (Button) v.findViewById(R.id.btn_cerrar_down);
        Button btn_cancelar_cita = (Button) v.findViewById(R.id.btn_cancelar_cita);
        et_descripcion_cancelar = (EditText) v.findViewById(R.id.respuesta_et_descripcion);
        final TextInputLayout content_descripcion = (TextInputLayout) v.findViewById(R.id.container_descripcion);
        respuesta_nombre.setText(Html.fromHtml("<b>Nombre: </b>"+ nombre));
        respuesta_cedula.setText(Html.fromHtml("<b>N&uacute;mero de c&eacute;dula: </b>"+ cedula));
        respuesta_email.setText(Html.fromHtml("<b>Email: </b>"+ email));
        respuesta_telefono.setText(Html.fromHtml("<b>Tel&eacute;fono: </b>"+ telefono));
        respuesta_fecha.setText(Html.fromHtml("<b>Fecha: </b>"+ fecha));
        respuesta_hora.setText(Html.fromHtml("<b>Hora: </b>"+hora));
        respuesta_tipo_cita.setText(Html.fromHtml("<b>Tipo de cita: </b>" +tipo_cita));
        respuesta_tipo_solicitud.setText(Html.fromHtml("<b>Tipo de solicitud: </b>"+tipo_solicitud));
        respuesta_estado.setText(Html.fromHtml("<b>Estado: </b>"+estado));
        respuesta_estado_pasaporte.setText(Html.fromHtml("<b>Estado de pasaporte: </b>" + estado_pasaporte));
        final AlertDialog.Builder alert = new AlertDialog.Builder(Cancelar.this);
        // this is set the view from XML inside AlertDialog
        alert.setView(v);
        final AlertDialog dialog = alert.create();
        dialog.show();
        btn_cerrar_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btn_cerrar_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        if(validacionCancel) {
            btn_cancelar_cita.setVisibility(View.VISIBLE);
            respuesta_validacionCancel.setVisibility(View.GONE);
        }
        else{
            btn_cancelar_cita.setVisibility(View.GONE);
            respuesta_validacionCancel.setVisibility(View.VISIBLE);

        }
        btn_cancelar_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validación del campo descripción

                boolean bol_descripcion_cancelar = isValid(et_descripcion_cancelar.getText().toString());
                content_descripcion.setError(bol_descripcion_cancelar ? "Este campo es obligatorio.":"Este campo es obligatorio.");
                content_descripcion.setErrorEnabled(!bol_descripcion_cancelar);
                if(bol_descripcion_cancelar) {
                    //Solo si es verdadero
                    content_descripcion.setErrorEnabled(false);


                    Call<ResponseCancel> cancelar = MyApiService.getApiService().cancel(id_cita, et_descripcion_cancelar.getText().toString(), MyApplication.getToken());
                    cancelar.enqueue(new Callback<ResponseCancel>() {
                        @Override
                        public void onResponse(Call<ResponseCancel> call, Response<ResponseCancel> response) {
                            dialog.cancel();
                            LayoutInflater inflater = getLayoutInflater();
                            View v = inflater.inflate(R.layout.content_mensaje, null);
                            TextView texto_mensaje = (TextView) v.findViewById(R.id.texto_mensaje);
                            Button btn_aceptar = (Button) v.findViewById(R.id.btn_aceptar);
                            texto_mensaje.setText(response.body().data.message);
                            final AlertDialog.Builder alert = new AlertDialog.Builder(Cancelar.this);
                            // this is set the view from XML inside AlertDialog
                            alert.setView(v);
                            final AlertDialog dialog = alert.create();
                            et_fecha_pago.setText("");
                            et_identificacion.setText("");
                            dialog.show();
                            btn_aceptar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<ResponseCancel> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
    public void enviar(){
        boolean bol_identificacion = isValid(et_identificacion.getText().toString());
        boolean bol_fecha_pago = isValid(et_fecha_pago.getText().toString());
        content_numero_identificacion.setError(bol_identificacion ? "Este campo es obligatorio.":"Este campo es obligatorio.");
        content_fecha_pago.setError(bol_fecha_pago ? "Este campo es obligatorio.":"Este campo es obligatorio.");
        content_numero_identificacion.setErrorEnabled(!bol_identificacion);
        content_fecha_pago.setErrorEnabled(!bol_fecha_pago);

        if(bol_fecha_pago && bol_identificacion){
            progress.show();
            content_numero_identificacion.setErrorEnabled(false);
            content_fecha_pago.setErrorEnabled(false);
            Call<ResponseProcess> consulta = MyApiService.getApiService().setOptionPasaporte(et_fecha_pago.getText().toString(),et_identificacion.getText().toString(),"1",MyApplication.getToken());
            consulta.enqueue(new Callback<ResponseProcess>() {
                @Override
                public void onResponse(Call<ResponseProcess> call, retrofit2.Response<ResponseProcess> response) {
                    if(response.isSuccessful()) {
                        cancelProgress();
                        ResponseProcess datos = response.body();
                        showMessage(datos.data.user_nombre, datos.data.user_numero_identificacion,datos.data.user_correo_electronico,datos.data.user_telefono,datos.data.fecha_cita,datos.data.hora_cita,datos.data.tipo_cita,datos.data.tipo_solicitud,datos.data.estado, datos.data.passport_estado, datos.data.id_cita, datos.data.validacionCancel);
                    }


                }

                @Override
                public void onFailure(Call<ResponseProcess> call, Throwable t) {
                    cancelProgress();
                    Log.e("Error",t.getLocalizedMessage().toLowerCase());
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.content_formulariored, null);
                    TextView texto_mensaje = (TextView) v.findViewById(R.id.texto_mensaje);
                    Button btn_aceptar = (Button) v.findViewById(R.id.btn_aceptar);
                    texto_mensaje.setText("La información ingresada no coincide con ningún registro de cita agendada.");
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Cancelar.this);
                    // this is set the view from XML inside AlertDialog
                    alert.setView(v);
                    final AlertDialog dialog = alert.create();
                    dialog.show();
                    btn_aceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
            });
        }{
            cancelProgress();
        }
    }
    private boolean isValid(String cadena){
        int textLength = cadena.length();
        return textLength > 0;
    }
}
