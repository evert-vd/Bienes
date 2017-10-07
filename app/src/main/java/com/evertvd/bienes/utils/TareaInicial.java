package com.evertvd.bienes.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.csvreader.CsvReader;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.ActivoAll;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.CentroCostoDao;
import com.evertvd.bienes.modelo.dao.CuentaContableDao;
import com.evertvd.bienes.modelo.dao.DepartamentoDao;
import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.ResponsableDao;
import com.evertvd.bienes.modelo.dao.SedeDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

public class TareaInicial extends AsyncTask<Void, Void, Void> {

    ProgressDialog progress;
    //DialogFragment dialogTask;
    Context context;
    String path;



    //para progresDialog
    public TareaInicial(ProgressDialog progress, Context context, String path) {
        this.progress = progress;
        this.context = context;
        this.path = path;
    }


    /*
    //para dialog fragment
    public TareaCarga(DialogFragment progress, Context context, String path) {
        this.dialogTask = progress;
        this.context = context;
        this.path=path;
    }
    */

    public void onPreExecute() {
        progress.show();

        //aquí se puede colocar código a ejecutarse previo
        //a la operación

        //hiloDepartamento=new HiloDepartamento(path);
        //hiloCentroCosto=new HiloCentroCosto(path);
        //hiloResponsable=new HiloResponsable(path);
        //hiloCuentaContable=new HiloCuentaContable(path);

    }


    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar

        progress.dismiss();
        //context.startActivity(new Intent(context,MainActivity.class));

        Intent intent=new Intent(context,MainActivity.class);
        //intent.putExtra("path", path);
        context.startActivity(intent);
        //context.startActivity(new Intent(context, MainActivity.class));
        //Toast.makeText(context, "Data Cargada correctamente", Toast.LENGTH_SHORT).show();
    }


    protected Void doInBackground(Void... params) {
        try{
            //realizar la operación aquí
            Long startTime = System.nanoTime();
            HiloSecundario hiloSecundario=new HiloSecundario(context,path);
            hiloSecundario.start();
            //hiloDepartamento.start();
            //hiloCentroCosto.start();
            //hiloResponsable.start();
            //hiloCuentaContable.start();
            Thread.sleep(400);
            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("Tiempo ejecutado", String.valueOf(time2));


        }catch (Exception e){

        }
        return null;
    }

}