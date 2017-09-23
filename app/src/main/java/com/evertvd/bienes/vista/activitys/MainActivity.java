package com.evertvd.bienes.vista.activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.vista.fragments.Login;
import com.evertvd.bienes.vista.fragments.Principal;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Activo> activoDaoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activoDaoList= Controller.getDaoSession().getActivoDao().loadAll();

        if(activoDaoList.isEmpty()|| activoDaoList==null){
            abrirFragmentLogin();

        }else{
           abrirFragmentPrincipal();

        }

    }

    private void abrirFragmentPrincipal() {
        FragmentManager fragmentManager = MainActivity.this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Principal principal = new Principal();
        transaction.replace(R.id.main_contenedor,principal);
        transaction.commit();

    }

    private void abrirFragmentLogin() {
        FragmentManager fragmentManager = MainActivity.this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Login login = new Login();
        transaction.replace(R.id.main_contenedor,login);
        transaction.commit();
    }
}
