package com.evertvd.bienes.hilos;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.utils.DirectorioCollage;
import com.evertvd.bienes.vista.activitys.ActivoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by evertvd on 18/09/2017.
 */

public class ThreadSaveCollage extends AsyncTask<Void, Void, Void> {
    private ProgressBar progressBar;
    private Button cancelar, guardar;
    private Context context;
    private String activo;

    //para progresDialog
    public ThreadSaveCollage(Context context, ProgressBar progressBar, Button cancelar, Button guardar, String activo) {
        this.context = context;
        this.progressBar = progressBar;
        this.cancelar = cancelar;
        this.guardar = guardar;
        this.context = context;
        this.activo = activo;
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
        //aquí se puede colocar código a ejecutarse previo
        //a la operación
        progressBar.setVisibility(View.VISIBLE);
        cancelar.setEnabled(false);
        guardar.setEnabled(false);
    }

    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar
        Intent intent=new Intent(context,ActivoView.class);
        intent.putExtra("activo",activo);
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    protected Void doInBackground(Void... params) {
        //aquí se puede colocar código que
        //se ejecutará en background

        try {
            copyFileFromCacheToInventario();
            Activo objActivo= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigo.eq(activo)).unique();
            objActivo.setFoto("Si");
            objActivo.setOrigenfoto("Aplicación");
            Controller.getDaoSession().getActivoDao().update(objActivo);
            boolean directoryOri = DirectorioCollage.deleteDirectory(DirectorioCollage.crearDirectorioOri(context));
            if (!directoryOri) {
                Log.e("ori","Error al eliminar directorio ori");
            }
            //borra directorio cache
            boolean directoryCache = DirectorioCollage.deleteDirectory(DirectorioCollage.crearDirectorioCache(context));
            if (!directoryCache) {
                Log.e("cache","Error al eliminar directorio cache");
            }
            Thread.sleep(100);
        } catch (Exception e) {
            Log.e("error","Error ejecutando hilo threadsavecollage");
        }

        return null;
    }


    private void copyFileFromCacheToInventario() {
        File origen = new File(DirectorioCollage.obtenerDirectorioCache(context, activo));
        File destino = new File(DirectorioCollage.obtenerDirectorioInventario(context, activo));
        try {
            InputStream in = new FileInputStream(origen);
            OutputStream out = new FileOutputStream(destino);
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {

        }
    }



}