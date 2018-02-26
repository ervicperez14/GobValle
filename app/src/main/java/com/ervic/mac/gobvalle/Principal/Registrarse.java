package com.ervic.mac.gobvalle.Principal;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.load.HttpException;
import com.ervic.mac.gobvalle.API.MyApiService;
import com.ervic.mac.gobvalle.Adapters.AdapterSpinner;
import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.BottomNavigationViewHelper;
import com.ervic.mac.gobvalle.MainActivity;
import com.ervic.mac.gobvalle.Models.DataUser;
import com.ervic.mac.gobvalle.Models.ErrorResponse;
import com.ervic.mac.gobvalle.Models.ResponseVerificaPago;
import com.ervic.mac.gobvalle.Models.Types;
import com.ervic.mac.gobvalle.Models.Error;
import com.ervic.mac.gobvalle.PreferenciasGobvalle;
import com.ervic.mac.gobvalle.R;
import com.ervic.mac.gobvalle.DataBase.*;
import com.ervic.mac.gobvalle.WebView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;

public class Registrarse extends AppCompatActivity {
    private ContentValues registro;
    private SQLiteDatabase _database;
    private String TAG_ID ="ID";
    private String TAG_EMAIL = "EMAIL";
    private String TAG_PHONE = "PHONE";
    private String TAG_PAYMENT_DATE = "DATE";
    private String TAG_REQUEST = "REQUEST";
    private AdminOpenHelper _adminDB;
    private String TAG_LOGGED = "LOGGED";
    private PreferenciasGobvalle my_preferences;
    private Button btn_enviar;
    EditText et_fecha;
    TextInputLayout c_correo, c_telefono, c_numero, c_fecha;
    EditText et_nombre, et_numero,et_telefono,et_correo;
    BottomNavigationView bottomNavigationView;
    ImageView btn_back;
    private AdapterSpinner mAdapter;
    private Spinner _spinnerRequest;
    private List<Types> typesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        et_fecha = (EditText) findViewById(R.id.fechapago);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_enviar = (Button) findViewById(R.id.btn_enviar);
        c_correo = (TextInputLayout) findViewById(R.id.container_correo);
        c_telefono = (TextInputLayout) findViewById(R.id.container_telefono);
        c_numero = (TextInputLayout) findViewById(R.id.container_numid);
        c_fecha = (TextInputLayout) findViewById(R.id.container_fechapago);
        et_correo = (EditText) findViewById(R.id.correo);
        et_numero = (EditText) findViewById(R.id.numid);
        et_telefono = (EditText) findViewById(R.id.telefono);
        my_preferences = new PreferenciasGobvalle(this);
        typesList = new  ArrayList<Types>();
        typesList = MyApplication.getTypes().types;
        _spinnerRequest = (Spinner) findViewById(R.id._spinnerRequest);
        mAdapter = new AdapterSpinner(this,typesList);
        _spinnerRequest.setAdapter(mAdapter);
        et_fecha = (EditText) findViewById(R.id.fechapago);
        _adminDB = new AdminOpenHelper(getApplicationContext());
        _database = _adminDB.getWritableDatabase();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registrarse.this, MainActivity.class);
                startActivity(intent);
            }
        });

        et_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        final ProgressDialog progress_enviar = new ProgressDialog(this);
        progress_enviar.setMessage("Verificando, por favor espere ...");
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correoError = isValidEmail(et_correo.getText().toString());
                boolean telefonoError = isValidPhone(et_telefono.getText().toString());
                boolean numeroError = isValid(et_numero.getText().toString());
                boolean fechaError = isValid(et_fecha.getText().toString());

//Dependiendo del valor booleando al validar el email, muestra el mensaje:
                c_correo.setError(correoError ? "Ingrese un correo válido" : "Ingrese un correo válido");
                c_telefono.setError(telefonoError ? "Ingrese un número valido": "Ingrese un número valido");
                c_numero.setError(numeroError ? "Campo obligatorio": "Campo obligatorio");
                c_fecha.setError(fechaError ? "Selecciona la fecha de pago": "Selecciona la fecha de pago");

                c_correo.setErrorEnabled(!correoError);
                c_telefono.setErrorEnabled(!telefonoError);
                c_numero.setErrorEnabled(!numeroError);
                c_fecha.setErrorEnabled(!fechaError);


                if( correoError && telefonoError && numeroError && fechaError){
                    progress_enviar.show();
                    registro = new ContentValues();
                    c_correo.setErrorEnabled(false);
                    c_telefono.setErrorEnabled(false);
                    c_numero.setErrorEnabled(false);
                    c_fecha.setErrorEnabled(false);
                    registro.put(Constantes.Tabla_usuario.COLUMNA_NUMERO_IDENTIFICACION,et_numero.getText().toString());
                    registro.put(Constantes.Tabla_usuario.COLUMNA_FECHA_PAGO,et_fecha.getText().toString().replace(" ",""));
                    registro.put(Constantes.Tabla_usuario.COLUMNA_TIPO_SOLICITUD,String.valueOf(_spinnerRequest.getSelectedItemPosition()));
                    registro.put(Constantes.Tabla_usuario.COLUMNA_TELEFONO,et_telefono.getText().toString());
                    registro.put(Constantes.Tabla_usuario.COLUMNA_CORREO,et_correo.getText().toString());


                    Call<ResponseVerificaPago>  pago = MyApiService.getApiService().getCheckPayment(MyApplication.getToken(),et_numero.getText().toString().trim(),et_fecha.getText().toString());
                    pago.enqueue(new Callback<ResponseVerificaPago>() {
                        @Override
                        public void onResponse(Call<ResponseVerificaPago> call, Response<ResponseVerificaPago> response) {
                            Log.e("Probando","jeje");
                            if(response.isSuccessful()){
                                Log.e("STATUS",response.body().data.message);
                                progress_enviar.dismiss();
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Registrarse.this);
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View dialoglayout = inflater.inflate(R.layout.alert_result,null);
                                Button btn_aceptar = (Button) dialoglayout.findViewById(R.id.aceptar);
                                btn_aceptar.setVisibility(View.GONE);
                                TextView tv_mensaje = (TextView) dialoglayout.findViewById(R.id.dm_text);
                                builder.setView(dialoglayout);
                                builder.create();
                                tv_mensaje.setText(response.body().data.message);
                                builder.show();

                                _database.insert(Constantes.Tabla_usuario.TABLA_USUARIO,null,registro);
                                my_preferences.saveElement(TAG_LOGGED,true);
                                my_preferences.savejsonString(TAG_ID,et_numero.getText().toString());
                                my_preferences.savejsonString(TAG_PAYMENT_DATE,et_fecha.getText().toString().trim());
                                my_preferences.savejsonString(TAG_EMAIL,et_correo.getText().toString());
                                my_preferences.savejsonString(TAG_PHONE,et_telefono.getText().toString());
                                my_preferences.savejsonString(TAG_REQUEST,String.valueOf(_spinnerRequest.getSelectedItemId()));
                                DataUser user = new DataUser();
                                user.setIdentificacion(et_numero.getText().toString());
                                user.setSolicitud(String.valueOf(MyApplication.getTypes().types.get(_spinnerRequest.getSelectedItemPosition()).value));
                                user.setCorreo(et_correo.getText().toString());
                                user.setTelefono(et_telefono.getText().toString());
                                user.setPago(et_fecha.getText().toString());
                                Log.e("DATOS",et_correo.getText().toString() + et_correo.getText().toString() +et_telefono.getText().toString());
                                MyApplication.set_dataUser(user);
                                Intent intent = new Intent(Registrarse.this,MainActivity.class);
                                startActivity(intent);

                            }else if(response.code() == 1){
                                Log.e("entro","probando");
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Registrarse.this);
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View dialoglayout = inflater.inflate(R.layout.alert_result,null);
                                Button btn_aceptar = (Button) dialoglayout.findViewById(R.id.aceptar);
                                TextView tv_mensaje = (TextView) dialoglayout.findViewById(R.id.dm_text);
                                builder.setView(dialoglayout);
                                builder.create();
                                tv_mensaje.setText(response.body().data.message);
                                builder.show();
                            }
                            _adminDB.close();

                        }

                        @Override
                        public void onFailure(Call<ResponseVerificaPago> call, Throwable t) {
                            //Snackbar.make(findViewById(R.id.btn_enviar),"Para realizar el respectivo registro debe haber realizado el primer pago.",Snackbar.LENGTH_LONG).show();
                            progress_enviar.dismiss();
                            LayoutInflater inflater = getLayoutInflater();
                            View v = inflater.inflate(R.layout.content_mensaje, null);
                            TextView texto_mensaje = (TextView) v.findViewById(R.id.texto_mensaje);
                            Button btn_aceptar = (Button) v.findViewById(R.id.btn_aceptar);
                            texto_mensaje.setText("Para realizar el respectivo registro debe haber realizado el primer pago.");
                            final AlertDialog.Builder alert = new AlertDialog.Builder(Registrarse.this);
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


                }



            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                //Correcciones
                String dia = String.valueOf(day);
                String mes = String.valueOf(month+1);
                String año = String.valueOf(year);

                if(dia.length()<2)
                    dia = "0"+dia;
                if(mes.length()<2)
                    mes = "0"+mes;
                final String selectedDate = dia + "/" + (mes) + "/" + año;
                et_fecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return !TextUtils.isEmpty(email) && pattern.matcher(email).matches();
    }

    private boolean isValid(String cadena){
        int textLength = cadena.length();
        return textLength > 0;
    }
    private boolean isValidPhone(String phone) {
        int textLength =phone.length();
        return textLength == 10 || textLength == 7;
    }
    @Override
    public void onBackPressed(){

        finish();
    }

}
