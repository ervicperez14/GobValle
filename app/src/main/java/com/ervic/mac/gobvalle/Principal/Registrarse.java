package com.ervic.mac.gobvalle.Principal;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ervic.mac.gobvalle.Adapters.AdapterSpinner;
import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.BottomNavigationViewHelper;
import com.ervic.mac.gobvalle.MainActivity;
import com.ervic.mac.gobvalle.Models.Types;
import com.ervic.mac.gobvalle.R;
import com.ervic.mac.gobvalle.WebView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Registrarse extends AppCompatActivity {
    EditText et_fecha;
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
        typesList = MyApplication.getTypes().types;
        _spinnerRequest = (Spinner) findViewById(R.id._spinnerRequest);
        mAdapter = new AdapterSpinner(this,typesList);
        _spinnerRequest.setAdapter(mAdapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registrarse.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }



}
