package com.evertvd.bienes.threads;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.utils.MainDirectorios;
import com.evertvd.bienes.vista.activitys.ActivoView;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


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
        if(!activo.contains("nn")){
            Intent intent=new Intent(context,ActivoView.class);
            intent.putExtra("activo",activo);
            context.startActivity(intent);
            ((Activity)context).finish();
        }else{
            context.startActivity(new Intent(context, MainActivity.class));
            ((Activity)context).finish();
        }

    }

    protected Void doInBackground(Void... params) {
        //aquí se puede colocar código que
        //se ejecutará en background

        try {
            if(!activo.contains("nn")){
                copyFotoFromTempToInventario();
                Activo objActivo= Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigo.eq(activo)).unique();
                objActivo.setFoto("Si");
                objActivo.setOrigenfoto("Aplicación");
                Controller.getDaoSession().getActivoDao().update(objActivo);
            }else{
                String fotoNN = context.getString(R.string.sinCodActivo);
                List<String> listFile = MainDirectorios.listarFotosNN(MainDirectorios.crearDirectorioFotos(context));
                //if (!listFile.isEmpty()) {
                    for (int i = 0; i < listFile.size(); i++) {
                        //valida que el archivo existe en el directorio para crear activo(n).csv
                        if (!validarNombreFotoNN(fotoNN + " (" + (i + 1) + ")"+ context.getString(R.string.extensionFoto))) {
                            fotoNN = fotoNN + " (" + (i + 1) + ")";
                            break;
                        }
                    }
                copyFotoNNFromTempToInventario(fotoNN);
            }

            boolean directoryOri = MainDirectorios.deleteDirectory(MainDirectorios.crearDirectorioFotosOri(context));
            if (!directoryOri) {
                Log.e("ori","Error al eliminar directorio ori");
            }
            //borra directorio cache
            boolean directoryCache = MainDirectorios.deleteDirectory(MainDirectorios.crearDirectorioFotosTemp(context));
            if (!directoryCache) {
                Log.e("cache","Error al eliminar directorio cache");
            }
            Thread.sleep(100);
        } catch (Exception e) {
            Log.e("error","Error ejecutando hilo threadsavecollage "+e.getMessage());
        }

        return null;
    }


    private void copyFotoFromTempToInventario() {
        File origen = new File(MainDirectorios.obtenerDirectorioFotosTemp(context, activo));
        File destino = new File(MainDirectorios.obtenerDirectorioFotos(context,activo));
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

    private void copyFotoNNFromTempToInventario(String nombreFotoNN) {
        File origen = new File(MainDirectorios.obtenerDirectorioFotosTemp(context, activo));
        File destino = new File(MainDirectorios.obtenerDirectorioFotos(context, nombreFotoNN));
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

    private boolean validarNombreFotoNN(String fotoNN){
        List<String>listFotosNN= MainDirectorios.listarFotosNN(MainDirectorios.crearDirectorioFotos(context));
        if(listFotosNN.contains(fotoNN)){
            return true;
        }else{
            return false;
        }
    }

}