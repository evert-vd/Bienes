package com.evertvd.bienes.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.evertvd.bienes.R;

import java.io.File;

/**
 * Created by evertvd on 02/10/2017.
 */

public class DirectorioCollage {

    public static final String TAG="logcat";

    public boolean isExternalStorageWritable(){
         String state= Environment.getExternalStorageState();
         return Environment.MEDIA_MOUNTED.equals(state);
     }


    public File crearDirectorioPublico(Context context){
        String nombreDirectorio="Inventario";
        if(isExternalStorageWritable()){
            File directorio=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getString(R.string.directorio));
            if(!directorio.mkdir()){
                Log.e(TAG,"No se creó el directorio");
            }
            return directorio;
        }else{
            return null;
        }
    }


}
