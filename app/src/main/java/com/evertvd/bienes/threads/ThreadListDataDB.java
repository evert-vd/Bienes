package com.evertvd.bienes.threads;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.csvreader.CsvWriter;
import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Historial;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.HistorialDao;
import com.evertvd.bienes.utils.MainDirectorios;
import com.evertvd.bienes.vista.activitys.MainActivity;
import com.evertvd.bienes.vista.dialogs.DialogWriteCsv;
import com.evertvd.bienes.vista.fragments.Login;
import com.evertvd.bienes.vista.fragments.Principal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by evertvd on 18/09/2017.
 */

public class ThreadListDataDB extends AsyncTask<Void, Void, Void> {

    private Context context;
    private int evento;
    private DialogWriteCsv dialogWriteCsv;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private FragmentManager fragmentManager;
    private List<Activo> activoList;



    public ThreadListDataDB(Context context, FragmentManager fragmentManager, ProgressBar progressBar) {
        this.context=context;
        //this.linearLayout=linearLayout;
        this.progressBar=progressBar;
        //this.evento=evento;
        this.fragmentManager=fragmentManager;


    }


    public void onPreExecute() {
        //aquí se puede colocar código a ejecutarse previo
        //a la operación;

        progressBar.setVisibility(View.VISIBLE);
        /*if(linearLayout!=null){
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }*/

        //dialogWriteCsv.show();
    }

    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar
        progressBar.setVisibility(View.GONE);
            if(activoList.isEmpty()){
                abrirFragmentLogin();
                //linearLayout.setVisibility(View.GONE);
                //progressBar.setVisibility(View.GONE);
                //Toast.makeText(context,"Data exportada correctamente",Toast.LENGTH_SHORT).show();

            }else{
            abrirFragmentPrincipal();
            }


        }

    protected Void doInBackground(Void... params) {
        //aquí se puede colocar código que
        //se ejecutará en background
        try{
            Long startTime = System.nanoTime();
            //activoList=Controller.getDaoSession().getActivoDao().loadAll();
            activoList=Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion).list();
            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("hiloWrite", String.valueOf(time2));
        }catch (Exception e){

        }
        return null;
    }


    private void abrirFragmentPrincipal() {
        //FragmentManager fragmentManager = context.fragmentManager;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Principal principal = new Principal();
        transaction.replace(R.id.content_main,principal);
        transaction.commit();
    }

    private void abrirFragmentLogin() {
        //FragmentManager fragmentManager = MainActivity.this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Login login = new Login();
        transaction.replace(R.id.content_main,login);
        transaction.commit();
    }

}