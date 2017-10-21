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

    public static final String TAG="directorio";

    public static  boolean isExternalStorageWritable(){
         String state= Environment.getExternalStorageState();
         return Environment.MEDIA_MOUNTED.equals(state);
     }

    public static File crearDirectorioInventario(Context context) {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),context.getString(R.string.inventario));
// Comprobamos si la carpeta está ya creada
// Si la carpeta no está creada, la creamos.
        if(!f.isDirectory()) {
            String newFolder = "/"+context.getString(R.string.inventario); //cualquierCarpeta es el nombre de la Carpeta que vamos a crear
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File folderInventario = new File(extStorageDirectory + newFolder);
            folderInventario.mkdir(); //creamos la carpeta
            return folderInventario;
        }else{

            Log.d(TAG,"La carpeta ya estaba creada");
            return f;
        }
    }


    public static File crearDirectorioCache(Context context) {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),context.getString(R.string.inventario)+"/"+context.getString(R.string.cache));
        // Comprobamos si la carpeta está ya creada
        // Si la carpeta no está creada, la creamos.
        if(!f.isDirectory()) {
            String newFolder = "/"+context.getString(R.string.inventario)+"/"+context.getString(R.string.cache); //nombre de la Carpeta que vamos a crear
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File folderCache = new File(extStorageDirectory + newFolder);
            folderCache.mkdir(); //creamos la carpeta
            return folderCache;
        }else{
            Log.d(TAG,"La carpeta ya estaba creada");
            return f;
        }
    }
    /*
    public static File crearDirectorioCache(Context context){
        if(isExternalStorageWritable()){
            File directorio=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getString(R.string.inventario)+"/"+context.getString(R.string.cache));
            if(!directorio.mkdir()){
                Log.e(TAG,"No se creó el directorio o ya está creado");
            }
            return directorio;
        }else{
            return null;
        }
    }
    */
    public static File crearDirectorioOri(Context context) {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),context.getString(R.string.inventario)+"/"+context.getString(R.string.ori));
       // Comprobamos si la carpeta está ya creada
       // Si la carpeta no está creada, la creamos.
         if(!f.isDirectory()) {
            String newFolder = "/"+context.getString(R.string.inventario)+"/"+context.getString(R.string.ori); //cualquierCarpeta es el nombre de la Carpeta que vamos a crear
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File folderOri = new File(extStorageDirectory + newFolder);
             folderOri.mkdir(); //creamos la carpeta
            return folderOri;
        }else{
            Log.d(TAG,"La carpeta ya estaba creada");
            return f;
        }
    }

    /*
    public static File crearDirectorioOri(Context context){
        if(isExternalStorageWritable()){
            File directorio=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getString(R.string.inventario)+"/"+context.getString(R.string.ori));
            if(!directorio.mkdir()){
                Log.e(TAG,"No se creó el directorio o ya está creado");
            }
            return directorio;
        }else{
            return null;
        }
    }
    */
    public static String obtenerDirectorioInventario(Context context, String activo){
        String nameFile= "/"+context.getString(R.string.inventario)+"/" +activo+ ".jpg";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+nameFile;

    }

    public static String obtenerDirectorioCache(Context context, String activo){
        String nameFile="/"+context.getString(R.string.inventario)+"/"+context.getString(R.string.cache)+"/" + activo +".jpg";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+nameFile;
    }

    public static String obtenerDirectorioOri(Context context, String activo,int requestcode){
        String nameFile="/"+context.getString(R.string.inventario)+"/"+context.getString(R.string.ori)+"/" + activo +"("+ requestcode +")"+ ".jpg";
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+nameFile;
    }

    public static boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            String[] children = directory.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDirectory(new File(directory, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return directory.delete();
    }

}
