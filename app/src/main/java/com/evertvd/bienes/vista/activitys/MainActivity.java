package com.evertvd.bienes.vista.activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.threads.ThreadListDataDB;
import com.evertvd.bienes.vista.fragments.Login;
import com.evertvd.bienes.vista.fragments.Principal;

import java.util.List;


public class MainActivity extends AppCompatActivity{
    private ProgressBar progressBar;
    private List<Activo> activoDaoList;

    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar=(ProgressBar)findViewById(R.id.progressLoad);
        FragmentManager fragmentManager = MainActivity.this.getFragmentManager();
        /*activoDaoList= Controller.getDaoSession().getActivoDao().loadAll();
        if(activoDaoList.isEmpty()){
            abrirFragmentLogin();
        }else{
           abrirFragmentPrincipal();
        }*/

        ThreadListDataDB threadListDataDB=new ThreadListDataDB(this,fragmentManager,progressBar );
        threadListDataDB.execute();

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
