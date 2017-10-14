package com.evertvd.bienes.utils;

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
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

    public class HiloTerciario extends Thread {
    private String path;
    Context context;

    public HiloTerciario(Context context, String path) {
        this.path=path;
        this.context=context;
    }

        @Override
        public void run() {

            Long startTime = System.nanoTime();

            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("hiloSec", String.valueOf(time2));

                try {
                    // Dejar libre la CPU durante
                    // unos milisegundos
                    Thread.sleep(100);
                    //context.getApplicationContext().startActivity();
                    leerArchivo();

                    //Toast.makeText(context,total.getTotal()+"registros Cargados",Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    return;
                }
            }


    private void leerArchivo() {

        try {

            CsvReader activos = new CsvReader(path);
            activos.readHeaders();

            int i=0;
            while (activos.readRecord()) {
                String empresa = activos.get("Empresa");
                String codigo = activos.get("Cod. Activo");
                String barras = activos.get("Cod. Barras");

                String ubicacion = activos.get("Ubicación");

                String nomCatalogo = activos.get("Catálogo");
                String placa = activos.get("Placa");
                String marca = activos.get("Marca");
                String modelo = activos.get("Modelo");
                String serie = activos.get("Serie");
                String foto = activos.get("Si/No");
                String responsable = activos.get("Responsable");
                String tipoActivo = activos.get("Tipo Activo");
                String activoPadre = activos.get("Cod. Activo Padre");
                String codCentro = activos.get("C. Resp");
                String centroCosto = activos.get("Centro Responsabilidad");
                String cuenta = activos.get("Cta. Ctble.");
                String estado = activos.get("Estado");
                String expediente = activos.get("Expediente");
                String ordenCompra = activos.get("Ord. Compra");
                String factura = activos.get("Fac. Compra");
                String fecCompra = activos.get("Fec. Compra");
                String observacion = activos.get("Observación");



                Activo activo = new Activo();
                activo.setCodigo(codigo);
                activo.setCodigobarra(barras);
                activo.setCatalogo_id(Buscar.buscarCatalogo(nomCatalogo).getId());
                activo.setEmpresa_id(Buscar.buscarEmpresa(empresa).getId());
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
                activo.setUbicacion_id(Buscar.buscarUbicacion(ubicacion).getId());
                activo.setCentrocosto_id(Buscar.buscarCentro(centroCosto).getId());
                activo.setCuentacontable_id(Buscar.buscarCuenta(cuenta).getId());
                activo.setResponsable_id(Buscar.buscarResponsable(responsable).getId());
                activo.setEstado(String.valueOf(i+1));
                Controller.getDaoSession().getActivoDao().insert(activo);
            i++;

            }

            activos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
