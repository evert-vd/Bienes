package com.evertvd.bienes.threads.grupo2;

import android.content.Context;
import android.util.Log;

import com.csvreader.CsvReader;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.utils.Buscar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

    public class HiloDepartamento extends Thread {
    private String path;
    Context context;

    public HiloDepartamento(ThreadGroup nombreGrupo,String nombreHilo, String path) {
        this.path=path;
        this.context=context;
    }

        @Override
        public void run() {

            Long startTime = System.nanoTime();

            leerArchivo();
            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("hiloDep", String.valueOf(time2));

                try {
                    // Dejar libre la CPU durante
                    // unos milisegundos
                    //Thread.sleep(100);
                    //context.getApplicationContext().startActivity();

                    //Toast.makeText(context,total.getTotal()+"registros Cargados",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    return;
                }
            }


    private void leerArchivo() {

        try {

            CsvReader activos = new CsvReader(path);
            activos.readHeaders();

            int i=0;
            while (activos.readRecord()) {

                String departamento = activos.get("Departamento");

                if(Buscar.buscarDepartamento(departamento)==null){
                    Departamento departamento1=new Departamento();
                    departamento1.setDepartamento(departamento);
                    Controller.getDaoSession().getDepartamentoDao().insert(departamento1);
                }
            }

            activos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
