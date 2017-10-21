package com.evertvd.bienes.hilos;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.utils.Buscar;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

    public class ThreadWriteData extends Thread {
    private String path;
    Context context;

    public ThreadWriteData(Context context) {
        //this.path=path;
        this.context=context;
    }

        @Override
        public void run() {

            Long startTime = System.nanoTime();

            escribirArchivo();


            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("hiloCC", String.valueOf(time2));

                try {
                    // Dejar libre la CPU durante
                    // unos milisegundos
                    //Thread.sleep(100);
                    //context.getApplicationContext().startActivity();
                    //Toast.makeText(context,total.getTotal()+"registros Cargados",Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context,MainActivity.class));
                } catch (Exception e) {
                    return;
                }
            }


    private void escribirArchivo() {
        //new File(Environment.getExternalStoragePublicDirectory
        String outputFile = "activos.csv";
        // before we open the file check to see if it already exists
        //boolean alreadyExists = new File(outputFile).exists();
        boolean alreadyExists = new File(Environment.getExternalStorageDirectory()+"/Activos+/"+outputFile).exists();

        try {
            List<Activo>activoList=Controller.getDaoSession().getActivoDao().loadAll();
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

            // if the file didn't already exist then we need to write out the header line
            if (!alreadyExists)
            {
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
                csvOutput.endRecord();
            }
            // else assume that the file already has the correct header line

            // write cuerpo
            for(int i=0;i<activoList.size();i++){
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
                 csvOutput.endRecord();
            }


            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
