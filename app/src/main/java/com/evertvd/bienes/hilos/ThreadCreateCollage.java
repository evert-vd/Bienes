package com.evertvd.bienes.hilos;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.evertvd.bienes.utils.DirectorioCollage;
import com.evertvd.bienes.vista.dialogs.DialogViewCollage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * Created by evertvd on 18/09/2017.
 */

public class ThreadCreateCollage extends AsyncTask<Void, Void, Void> {
    private ProgressDialog dialog;
    private Context context;
    private String activo;
    private Bitmap bitmap;
    private int textPositionY;
    private FragmentManager fragmentManager;

    //para progresDialog
    public ThreadCreateCollage(ProgressDialog dialog, Context context, String activo, Bitmap bitmap, FragmentManager fragmentManager, int textPositionY) {
        //this.activity=activity;
        this.dialog=dialog;
        this.activo=activo;
        this.bitmap=bitmap;
        this.context=context;
        this.textPositionY=textPositionY;
        this.fragmentManager=fragmentManager;
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
        dialog.show();
    }

    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar
        dialog.dismiss();
            //guarda en cache y abre el dialog fragment
            //Toast.makeText(context,"Foto Guardada correctamento",Toast.LENGTH_SHORT).show();
            DialogFragment dialogFragment = new DialogViewCollage();
            Bundle bundle=new Bundle();
            bundle.putString("activo",activo);
            dialogFragment.setArguments(bundle);
            dialogFragment.setCancelable(false);
            dialogFragment.show(fragmentManager, "dialogoFotoview");
             //((Activity)context).finish();//finaliza la actividad
        }

    protected Void doInBackground(Void... params) {
        //aquí se puede colocar código que
        //se ejecutará en background
        try{
            guardarImagen(bitmap);
        }catch (Exception e){

        }
        return null;
    }

    private void guardarImagen(Bitmap imagen) {
        //se guarda la imagen en directorio cache
        Canvas c = new Canvas (imagen);
        Paint paint = new Paint();

        paint.setColor(Color.GRAY);
        c.drawRect(3,textPositionY-3,135,textPositionY-70,paint);
        Log.e("y",String.valueOf(textPositionY));


        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(35);
        //c.drawText(text,initPositionX ,initPositionY, paint);
        c.drawText(activo,20 ,textPositionY-22, paint);

        OutputStream fileOutStream = null;
        Uri uri;
        try {
            File directorioImagenes=null;
                //grabar activo
                directorioImagenes = new File(DirectorioCollage.crearDirectorioCache(context), activo+".jpg");
             uri = Uri.fromFile(directorioImagenes);
            fileOutStream = new FileOutputStream(directorioImagenes);
        } catch (Exception e) {
            Log.e("ERROR!", e.getMessage());
        }

        try {
            imagen.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
            fileOutStream.flush();
            fileOutStream.close();
        } catch (Exception e) {
            Log.e("ERROR!", e.getMessage());
        }
    }

}