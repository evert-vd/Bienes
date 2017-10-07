package com.evertvd.bienes.vista.activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.vista.fragments.PrincipalOpc2;
import com.evertvd.bienes.vista.fragments.ViewActivo;

import java.util.List;

public class ActivoActivity extends AppCompatActivity {


    private String numActivo;
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

