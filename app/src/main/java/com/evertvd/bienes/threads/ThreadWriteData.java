package com.evertvd.bienes.threads;

import android.content.Context;
import android.os.AsyncTask;
import android.app.FragmentManager;
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
import com.evertvd.bienes.vista.dialogs.DialogWriteCsv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by evertvd on 18/09/2017.
 */

public class ThreadWriteData extends AsyncTask<Void, Void, Void> {

    private Context context;
    private int evento;
    private DialogWriteCsv dialogWriteCsv;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private FragmentManager fragmentManager;



    public ThreadWriteData(Context context, LinearLayout linearLayout, ProgressBar progressBar, int evento) {
        this.context=context;
        this.linearLayout=linearLayout;
        this.progressBar=progressBar;
        this.evento=evento;
    }


    public void onPreExecute() {
        //aquí se puede colocar código a ejecutarse previo
        //a la operación;

        if(linearLayout!=null){
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        //dialogWriteCsv.show();
    }

    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar

            linearLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(context,"Data exportada correctamente",Toast.LENGTH_SHORT).show();

        }

    protected Void doInBackground(Void... params) {
        //aquí se puede colocar código que
        //se ejecutará en background
        try{
            Long startTime = System.nanoTime();

            //escribirArchivo();
            if(evento==1){
                String nuevoNombre = context.getString(R.string.nombreFileExport);
                List<String> listFile = MainDirectorios.listarInventarioCSV(MainDirectorios.crearDirectorioExport(context));
                if (!listFile.isEmpty()) {
                    for (int i = 0; i < listFile.size(); i++) {
                       //valida que el archivo existe en el directorio para crear activo(n).csv
                        if (!validarNombreArchivo(nuevoNombre + " (" + (i + 1) + ")"+ context.getString(R.string.extensionFileExport))) {
                            nuevoNombre = nuevoNombre + " (" + (i + 1) + ")" + context.getString(R.string.extensionFileExport);
                            break;
                        }
                    }
                    writeCsv(nuevoNombre);
                }

            }else if(evento==2) {
                //MainDirectorios.eliminarArchivos(MainDirectorios.crearDirectorioExport(context));
                MainDirectorios.eliminarFichero(context);
                writeCsv(context.getString(R.string.nombreFileExport)+context.getString(R.string.extensionFileExport));
            }else{
                writeCsv(context.getString(R.string.nombreFileExport)+context.getString(R.string.extensionFileExport));
            }

            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("hiloWrite", String.valueOf(time2));
        }catch (Exception e){

        }
        return null;
    }


    private void writeCsv(String nombreArchivo){
        File file= MainDirectorios.crearDirectorioExport(context);
        // before we open the file check to see if it already exists
        //boolean alreadyExists = new File(outputFile).exists();
        boolean alreadyExists = new File(file+"/"+nombreArchivo).exists();

        try {
            // use FileWriter constructor that specifies open for appending
            List<Activo> activoList= Controller.getDaoSession().getActivoDao().queryBuilder().orderAsc(ActivoDao.Properties.Descripcion).list();

            CsvWriter csvOutput = new CsvWriter(new FileWriter(file+"/"+nombreArchivo, true), ',');

            // if the file didn't already exist then we need to write out the header line
            if (!alreadyExists) {
                //cabeceras
                csvOutput.write("Cod. Activo");
                csvOutput.write("Cod. Barras");
                csvOutput.write("Cod. Activo Padre");
                csvOutput.write("Departamento");
                csvOutput.write("Sede");
                csvOutput.write("Ubicación");
                csvOutput.write("Cod. Catálogo");
                csvOutput.write("Catálogo");
                csvOutput.write("Placa");
                csvOutput.write("Marca");
                csvOutput.write("Modelo");
                csvOutput.write("Serie");
                csvOutput.write("Si/No");//foto
                csvOutput.write("Tipo Activo");
                csvOutput.write("C. Resp");
                csvOutput.write("Centro Responsabilidad");
                csvOutput.write("Cta. Ctble.");
                csvOutput.write("Estado");
                csvOutput.write("Expediente");
                csvOutput.write("Ord. Compra");
                csvOutput.write("Fac. Compra");
                csvOutput.write("Fec. Compra");
                csvOutput.write("Observación");
                csvOutput.write("Empresa");
                csvOutput.write("Campos Modificados");
                csvOutput.endRecord();

                // else assume that the file already has the correct header line

                // write out a few records
                for(int i=0;i<activoList.size();i++) {
                    csvOutput.write(activoList.get(i).getCodigo());
                    csvOutput.write(activoList.get(i).getCodigobarra());
                    csvOutput.write(activoList.get(i).getActivopadre());
                    csvOutput.write(activoList.get(i).getUbicacion().getSede().getDepartamento().getDepartamento());
                    csvOutput.write(activoList.get(i).getUbicacion().getSede().getSede());
                    csvOutput.write(activoList.get(i).getUbicacion().getUbicacion());
                    csvOutput.write(activoList.get(i).getCatalogo().getCodigo());
                    csvOutput.write(activoList.get(i).getCatalogo().getCatalogo());
                    csvOutput.write(activoList.get(i).getPlaca());
                    csvOutput.write(activoList.get(i).getMarca());
                    csvOutput.write(activoList.get(i).getModelo());
                    csvOutput.write(activoList.get(i).getSerie());
                    csvOutput.write(activoList.get(i).getFoto());
                    csvOutput.write(activoList.get(i).getTipo());
                    csvOutput.write(activoList.get(i).getCentroCosto().getCodigo());
                    csvOutput.write(activoList.get(i).getCentroCosto().getCentro());
                    csvOutput.write(activoList.get(i).getCuentaContable().getCodigo());
                    csvOutput.write(activoList.get(i).getEstado());
                    csvOutput.write(activoList.get(i).getOrdencompra());
                    csvOutput.write(activoList.get(i).getFactura());
                    csvOutput.write(activoList.get(i).getFechacompra());
                    csvOutput.write(activoList.get(i).getDescripcion());
                    csvOutput.write(activoList.get(i).getCatalogo().getEmpresa().getEmpresa());

                    List<Historial>historialList=Controller.getDaoSession().getHistorialDao().queryBuilder().where(HistorialDao.Properties.Activo_id.eq(activoList.get(i).getId())).list();
                    String  historial1="";
                    String historial2="";
                    if(!historialList.isEmpty()){
                        for (int j=0;j<historialList.size();j++){
                            if(j<historialList.size()-1){
                                historial1+=historialList.get(j).getCampo_modificado()+"|";
                            }else{
                                historial2=historialList.get(j).getCampo_modificado();
                            }
                        }
                    }
                    csvOutput.write(historial1+historial2);
                    csvOutput.endRecord();
                }

            }

            csvOutput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean validarNombreArchivo(String nuevoArchivo){
        List<String>listFile= MainDirectorios.listarInventarioCSV(MainDirectorios.crearDirectorioExport(context));
        if(listFile.contains(nuevoArchivo)){
            return true;
        }else{
            return false;
        }
    }

}