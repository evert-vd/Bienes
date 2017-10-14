package com.evertvd.bienes.tareas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.evertvd.bienes.tareas.independientes.HiloActivo;
import com.evertvd.bienes.tareas.independientes.HiloCatalogo;
import com.evertvd.bienes.tareas.independientes.HiloCentroCosto;
import com.evertvd.bienes.tareas.independientes.HiloCuentaContable;
import com.evertvd.bienes.tareas.independientes.HiloDepartamento;
import com.evertvd.bienes.tareas.independientes.HiloEmpresa;
import com.evertvd.bienes.tareas.independientes.HiloResponsable;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 14/10/2017.
 */

public class Hilo1 extends AsyncTask<Void, Void, Void> {

    //ProgressDialog progress;
    //DialogFragment dialogTask;
    Context context;
    String path;

    HiloDepartamento hiloDepartamento;
    HiloCentroCosto hiloCentroCosto;
    HiloResponsable hiloResponsable;
    HiloCuentaContable hiloCuentaContable;
    HiloCatalogo hiloCatalogo;
    HiloEmpresa hiloEmpresa;
    HiloActivo hiloActivo;

    //para progresDialog
    public Hilo1(Context context, String path) {
        //this.progress = progress;
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
        //progress.show();

        //aquí se puede colocar código a ejecutarse previo
        //a la operación

        hiloDepartamento=new HiloDepartamento(path);
        hiloCentroCosto=new HiloCentroCosto(path);
        hiloResponsable=new HiloResponsable(path);
        hiloCuentaContable=new HiloCuentaContable(path);
        hiloCatalogo=new HiloCatalogo(path);
        hiloEmpresa=new HiloEmpresa(path);
        hiloActivo=new HiloActivo(context,path);

    }


    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar

        //progress.dismiss();
        //context.startActivity(new Intent(context,MainActivity.class));


        context.startActivity(new Intent(context, MainActivity.class));
        //Toast.makeText(context, "Data Cargada correctamente", Toast.LENGTH_SHORT).show();
    }


    protected Void doInBackground(Void... params) {
        try{
            //realizar la operación aquí
            Long startTime = System.nanoTime();
            //HiloSecundario hiloSecundario=new HiloSecundario(context,path);
            //hiloSecundario.start();

            hiloDepartamento.start();
            hiloCentroCosto.start();
            hiloResponsable.start();
            hiloCuentaContable.start();
            hiloEmpresa.start();
            hiloCatalogo.start();
            hiloActivo.start();
            //Thread.sleep(20);
            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("Tiempo total ejecutado", String.valueOf(time2));


        }catch (Exception e){

        }
        return null;
    }
}
