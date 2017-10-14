package com.evertvd.bienes.vista.activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.evertvd.bienes.R;
import com.evertvd.bienes.vista.fragments.ViewActivo;

public class ActivoActivity extends AppCompatActivity {


    public String numActivo;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activo);
        numActivo=getIntent().getExtras().getString("activo");
        //activo= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigo.eq(getIntent().getExtras().getString("activo"))).unique();

        toolbar = (Toolbar) findViewById(R.id.toolbarActivo);
        setSupportActionBar(toolbar);

        if(numActivo!=null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            ViewActivo viewActivo = new ViewActivo();
            transaction.replace(R.id.content_activo,viewActivo);
            transaction.commit();
        }
    }



    }

