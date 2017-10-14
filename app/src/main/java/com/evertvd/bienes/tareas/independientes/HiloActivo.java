package com.evertvd.bienes.tareas.independientes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.csvreader.CsvReader;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.utils.Buscar;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

    public class HiloActivo extends Thread {
    private String path;
    Context context;

    public HiloActivo(Context context, String path) {
        this.path=path;
        this.context=context;
    }

    @Override
    public void run() {

        Long startTime = System.nanoTime();
        leerArchivo();
        long endTime = System.nanoTime();
        int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        Log.e("hiloActivo1", String.valueOf(time2));

        try {
            // Dejar libre la CPU durante
            // unos milisegundos
            //Thread.sleep(100);
            context.startActivity(new Intent(context,MainActivity.class));
            //context.getApplicationContext().startActivity(getApplicationContext(),MainActivity.class);

            //Toast.makeText(context,total.getTotal()+"registros Cargados",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            return;
        }
    }


    private void leerArchivo() {

        try {

            CsvReader activos = new CsvReader(path);
            activos.readHeaders();


            while (activos.readRecord()) {

                String codigo = activos.get("Cod. Activo");
                String barras = activos.get("Cod. Barras");

                String placa = activos.get("Placa");
                String marca = activos.get("Marca");
                String modelo = activos.get("Modelo");
                String serie = activos.get("Serie");
                String foto = activos.get("Si/No");

                String tipoActivo = activos.get("Tipo Activo");
                String activoPadre = activos.get("Cod. Activo Padre");

                String estado = activos.get("Estado");
                String expediente = activos.get("Expediente");
                String ordenCompra = activos.get("Ord. Compra");
                String factura = activos.get("Fac. Compra");
                String fecCompra = activos.get("Fec. Compra");
                String observacion = activos.get("Observación");



                Activo activo = new Activo();
                activo.setCodigo(codigo);
                activo.setCodigobarra(barras);

                activo.setPlaca(placa);
                activo.setMarca(marca);
                activo.setModelo(modelo);
                activo.setSerie(serie);
                //setFoto
                //familia
                //subfamilia
                activo.setTipo(tipoActivo);
                activo.setActivopadre(activoPadre);
                activo.setEstado(estado);
                activo.setExpediente(expediente);
                activo.setOrdencompra(ordenCompra);
                activo.setFactura(factura);
                activo.setFechacompra(fecCompra);
                activo.setDescripcion(observacion);
                Controller.getDaoSession().getActivoDao().insert(activo);

            }

            activos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        }
