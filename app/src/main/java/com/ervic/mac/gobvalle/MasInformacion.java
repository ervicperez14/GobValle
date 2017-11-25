package com.ervic.mac.gobvalle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ervic.mac.gobvalle.Adapters.AdapterInformacion;
import com.ervic.mac.gobvalle.Application.MyApplication;
import com.ervic.mac.gobvalle.Models.leftMenu;

import java.util.List;

public class MasInformacion extends AppCompatActivity {
    private RecyclerView mRecycler;
    private AdapterInformacion mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_informacion);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_menulateral);
        mRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        List<leftMenu> lista = MyApplication.getData().leftMenu;
        mAdapter = new AdapterInformacion(this,lista);
        mRecycler.setAdapter(mAdapter);


    }
}
