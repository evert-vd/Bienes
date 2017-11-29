package com.evertvd.bienes.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.evertvd.bienes.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evertvd on 02/10/2017.
 */

public class MainDirectorios {

    public static final String TAG="directorio";

    public static  boolean isExternalStorageWritable(){
         String state= Environment.getExternalStorageState();
         return Environment.MEDIA_MOUNTED.equals(state);
     }

    public static File crearDirectorioFotos(Context context) {
        File carpetaInventario = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),context.getString(R.string.directorioFotos));
// Comprobamos si la carpeta está ya creada
// Si la carpeta no está creada, la creamos.
        if(!carpetaInventario.isDirectory()) {
            String directorioFotos = "/"+context.getString(R.string.directorioFotos); //nombre de la Carpeta que vamos a crear
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File folderInventario = new File(extStorageDirectory + directorioFotos);
            folderInventario.mkdir(); //creamos la carpeta
            return folderInventario;
        }else{
            Log.d(TAG,"La carpeta ya estaba creada");
            return carpetaInventario;
        }
    }


    public static File crearDirectorioFotosTemp(Context context) {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),context.getString(R.string.directorioFotos)+"/"+context.getString(R.string.temp));
        // Comprobamos si la carpeta está ya creada
        // Si la carpeta no está creada, la creamos.
        if(!f.isDirectory()) {
            String newFolder = "/"+context.getString(R.string.directorioFotos)+"/"+context.getString(R.string.temp); //nombre de la Carpeta que vamos a crear
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File folderCache = new File(extStorageDirectory + newFolder);
            folderCache.mkdir(); //creamos la carpeta
            return folderCache;
        }else{
            Log.d(TAG,"La carpeta ya estaba creada");
            return f;
        }
    }

    public static File crearDirectorioFotosOri(Context context) {
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),context.getString(R.string.directorioFotos)+"/"+context.getString(R.string.ori));
       // Comprobamos si la carpeta está ya creada
       // Si la carpeta no está creada, la creamos.
         if(!f.isDirectory()) {
            String newFolder = "/"+context.getString(R.string.directorioFotos)+"/"+context.getString(R.string.ori); //cualquierCarpeta es el nombre de la Carpeta que vamos a crear
            String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File folderOri = new File(extStorageDirectory + newFolder);
             folderOri.mkdir(); //creamos la carpeta
            return folderOri;
        }else{
            Log.d(TAG,"La carpeta ya estaba creada");
            return f;
        }
    }


    public static File crearDirectorioExport(Context context) {
        File f = new File(Environment.getExternalStorageDirectory(),context.getString(R.string.directorioFileExport));//inventario
        // Comprobamos si la carpeta está ya creada
        // Si la carpeta no está creada, la creamos.
        if(!f.isDirectory()) {
            String newFolder = "/"+context.getString(R.string.directorioFileExport); //nombre de la Carpeta que vamos a crear
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folderFile = new File(extStorageDirectory + newFolder);
            folderFile.mkdir(); //creamos la carpeta
            return folderFile;
        }else{
            Log.d(TAG,"La carpeta ya estaba creada");
            return f;
        }
    }
    //comprueba si existe el archivo
    public static boolean obtenerNombreFileExport(Context context){
        File file= MainDirectorios.crearDirectorioExport(context);
        boolean alreadyExists = new File(file+"/"+context.getString(R.string.nombreFileExport)+context.getString(R.string.extensionFileExport)).exists();
        return alreadyExists;
    }


    public static String obtenerDirectorioFotos(Context context, String activo){
        String nameFile= "/"+context.getString(R.string.directorioFotos)+"/"+activo+context.getString(R.string.extensionFoto);
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+nameFile;
    }

    public static String obtenerDirectorioFotosTemp(Context context, String activo){
        String nameFile="/"+context.getString(R.string.directorioFotos)+"/"+context.getString(R.string.temp)+"/" + activo +context.getString(R.string.extensionFoto);
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+nameFile;
    }

    public static String obtenerDirectorioFotosOri(Context context, String activo, int requestcode){
        String nameFile="/"+context.getString(R.string.directorioFotos)+"/"+context.getString(R.string.ori)+"/" + activo +"("+ requestcode +")"+context.getString(R.string.extensionFoto);
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


    public static void eliminarFichero(Context context){
        File f = new File(Environment.getExternalStorageDirectory(),context.getString(R.string.directorioFileExport)+"/"+context.getString(R.string.nombreFileExport)+context.getString(R.string.extensionFileExport));
        Log.e("file",f.toString());
        if(f.delete()){
            Log.e("TAG","archivo eliminado");
        }
    }

    public static List<String> listarInventarioCSV(File f){
         List<String> item = new ArrayList<>();

        //Defino la ruta donde busco los ficheros
        //Creo el array de tipo File con el contenido de la carpeta
        File[] files = f.listFiles();

        //Hacemos un Loop por cada fichero para extraer el nombre de cada uno
        for (int i = 0; i < files.length; i++) {
            //Sacamos del array files un fichero
            File file = files[i];
            //Si es directorio...
            if (file.isDirectory())
                item.add(file.getName() + "/");
                //Si es fichero...
            else
                item.add(file.getName());
        }
        return item;
    }


    public static List<String> listarFotosNN(File f){
        List<String> item = new ArrayList<>();
        //Defino la ruta donde busco los ficheros
        //Creo el array de tipo File con el contenido de la carpeta
        File[] files = f.listFiles();
        //Hacemos un Loop por cada fichero para extraer el nombre de cada uno
        for (int i = 0; i < files.length; i++) {
            //Sacamos del array files un fichero
            File file = files[i];
            //Si es fichero...
            if (!file.isDirectory()&&file.getName().contains("nn")) {
                item.add(file.getName());
            }
        }
        return item;
    }

}
