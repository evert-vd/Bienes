package com.evertvd.bienes.utils;

import android.util.Log;

import com.csvreader.CsvReader;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.ActivoAll;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 28/09/2017.
 */

public class JavaCsvData {

    String paht;
    ActivoAll activoAll;
    JavaCsvData(ActivoAll activoAll,String path){
this.activoAll=activoAll;
        this.paht=path;
    }



    public void leerArchivo(){
//realizar la operación aquí
        Long startTime = System.nanoTime();


        try {

            CsvReader activos = new CsvReader(paht);
            activos.readHeaders();
                /*

            ArrayList<String> sedeList=new ArrayList<String>();
            ArrayList<String> ubicacionList=new ArrayList<String>();
            ArrayList<String> codCatalogoList=new ArrayList<String>();
            ArrayList<String> responsableList=new ArrayList<String>();
            ArrayList<String> codCentroList=new ArrayList<String>();
            ArrayList<String> cuentaList=new ArrayList<String>();
            */

            while (activos.readRecord()) {

                String empresa = activos.get("Empresa");
                String codigo = activos.get("Cod. Activo");
                String barras = activos.get("Cod. Barras");
                String departamento = activos.get("Departamento");
                String sede = activos.get("Sede");
                String ubicacion = activos.get("Ubicación");
                String codCatalogo = activos.get("Cod. Catálogo");
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

                activoAll=new ActivoAll();
                activoAll.setCodigo(codigo);
                activoAll.setEmpresa(empresa);
                activoAll.setCodigobarra(barras);
                activoAll.setDepartamento(departamento);
                activoAll.setSede(sede);
                activoAll.setUbicacion(ubicacion);
                activoAll.setCodcatalogo(codCatalogo);
                activoAll.setCatalogo(nomCatalogo);
                activoAll.setPlaca(placa);
                activoAll.setMarca(marca);
                activoAll.setModelo(modelo);
                activoAll.setSerie(serie);
                activoAll.setFoto(foto);
                activoAll.setResponsable(responsable);
                activoAll.setTipoActivo(tipoActivo);
                activoAll.setActivopadre(activoPadre);
                activoAll.setCodCentro(codCentro);
                activoAll.setCentro(centroCosto);
                activoAll.setCuenta(cuenta);
                activoAll.setEstado(estado);
                activoAll.setExpediente(expediente);
                activoAll.setOrdencompra(ordenCompra);
                activoAll.setFactura(factura);
                activoAll.setFechacompra(fecCompra);
                activoAll.setObservacion(observacion);
                Controller.getDaoSession().getActivoAllDao().insert(activoAll);
            }

            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("Tiempo ejecutado",String.valueOf(time2));
            activos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
