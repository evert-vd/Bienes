package com.evertvd.bienes.tareas.independientes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.csvreader.CsvReader;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Total;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.utils.Buscar;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

    public class HiloCuentaContable extends Thread {
    private String path;
    Context context;

    public HiloCuentaContable( String path) {
        this.path=path;
        this.context=context;
    }

        @Override
        public void run() {

            Long startTime = System.nanoTime();
            leerArchivo();



            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("hiloCtaCble", String.valueOf(time2));

                try {
                    // Dejar libre la CPU durante
                    // unos milisegundos
                    //Thread.sleep(100);


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

                String cuenta = activos.get("Cta. Ctble.");



                if(Buscar.buscarCuenta(cuenta)==null){
                    CuentaContable cuentaContable=new CuentaContable();
                    cuentaContable.setCodigo(cuenta);
                    Controller.getDaoSession().getCuentaContableDao().insert(cuentaContable);
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
