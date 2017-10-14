package com.evertvd.bienes.vista.activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.ActivoAll;
import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.vista.fragments.Login;
import com.evertvd.bienes.vista.fragments.Principal;
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnCloseClickListener;

import java.util.List;


public class MainActivity extends AppCompatActivity{

    private List<Activo> activoDaoList;
    private List<Empresa>empresaList;
    private List<ActivoAll>activoAllList;
    private String path;

    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activoDaoList= Controller.getDaoSession().getActivoDao().loadAll();
        empresaList= Controller.getDaoSession().getEmpresaDao().loadAll();

       //List<ActivoAll> activoAll=Controller.getDaoSession().getActivoAllDao().loadAll();



        if(empresaList.isEmpty()){
            abrirFragmentLogin();
        }else{
           abrirFragmentPrincipal();
            Log.e("emp", String.valueOf(empresaList.size()));
        }

    }


    private void abrirFragmentPrincipal() {
        FragmentManager fragmentManager = MainActivity.this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Principal principal = new Principal();
        transaction.replace(R.id.content_main,principal);
        transaction.commit();
    }

    private void abrirFragmentLogin() {
        FragmentManager fragmentManager = MainActivity.this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Login login = new Login();
        transaction.replace(R.id.content_main,login);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        //finish();
        //super.onBackPressed();
    }
}
