package com.evertvd.bienes.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;


import java.util.List;


/**
 * Created by evertvd on 18/09/2017.
 */

public class TareaLecturaBD extends AsyncTask<Void, Void, Void> {

    ProgressDialog progress;
    //Main3Activity act;
    Context context;
    String path;
    List<Activo> activoList;

    public TareaLecturaBD(ProgressDialog progress, Context context) {
        this.progress = progress;
        this.context = context;

    }

    public void onPreExecute() {
        progress.show();
        //aquí se puede colocar código a ejecutarse previo
        //a la operación
    }

    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar
        progress.dismiss();

        //context.startActivity(new Intent(context,MainActivity.class));
       //for (int i=0;i<activoList.size();i++){
           // Log.e("codActivo",activoList.get(i).getCodigo()+" Ubicacion:"+activoList.get(i).getUbicacion()+ " Catalogo:"+activoList.get(i).getCatalogo());
        //}


    }

    protected Void doInBackground(Void... params) {

        //realizar la operación aquí
        cargarData();

        return null;
    }

    private void cargarData(){
       activoList= Controller.getDaoSession().getActivoDao().loadAll();
        for (int i=0;i<activoList.size();i++){
            Log.e("codActivo",activoList.get(i).getCodigo()+" Ubicacion:"+activoList.get(i).getUbicacion()+ " Catalogo:"+activoList.get(i).getCatalogo());
        }
    }

}
