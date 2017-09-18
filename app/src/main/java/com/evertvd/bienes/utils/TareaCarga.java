package com.evertvd.bienes.utils;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.csvreader.CsvReader;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

public class TareaCarga  extends AsyncTask<Void, Void, Void> {

        ProgressDialog progress;
        MainActivity act;
    Context context;
    String path;

        public TareaCarga(ProgressDialog progress, Context context, String path) {
            this.progress = progress;
            this.context = context;
            this.path=path;
        }

        public void onPreExecute() {
            progress.show();
        //aquí se puede colocar código a ejecutarse previo
        //a la operación
        }

        public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar
            progress.dismiss();

            context.startActivity(new Intent(context,MainActivity.class));
            Toast.makeText(context,"Data Cargada correctamente",Toast.LENGTH_SHORT).show();
        }

        protected Void doInBackground(Void... params) {

            //realizar la operación aquí
            Long startTime = System.nanoTime();


            try {

                CsvReader activos = new CsvReader(path);
                activos.readHeaders();
            /*
            ArrayList<String> empresaList=new ArrayList<String>();
            ArrayList<String> departamentoList=new ArrayList<String>();
            ArrayList<String> sedeList=new ArrayList<String>();
            ArrayList<String> ubicacionList=new ArrayList<String>();
            ArrayList<String> codCatalogoList=new ArrayList<String>();
            ArrayList<String> responsableList=new ArrayList<String>();
            ArrayList<String> codCentroList=new ArrayList<String>();
            ArrayList<String> cuentaList=new ArrayList<String>();
            */

                while (activos.readRecord()) {
                    String codigo = activos.get("Cod. Activo");
                    String barras = activos.get("Cod. Barras");
                    String empresa = activos.get("Empresa");
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
                    String CentroCosto = activos.get("Centro Responsabilidad");
                    String cuenta = activos.get("Cta. Ctble.");
                    String estado = activos.get("Estado");
                    String expediente = activos.get("Expediente");
                    String ordenCompra = activos.get("Ord. Compra");
                    String factura = activos.get("Fac. Compra");
                    String fecCompra = activos.get("Fec. Compra");
                    String observacion = activos.get("Observacion");

                    cargarDatos(codigo, barras, empresa, departamento, sede, ubicacion, codCatalogo, nomCatalogo,
                            placa, marca, modelo, serie, foto, responsable, tipoActivo, activoPadre, codCentro, CentroCosto, cuenta, estado,
                            expediente, ordenCompra, factura, fecCompra, observacion);



                /*
                if (!empresaList.contains(empresa)) {
                    empresaList.add(empresa);
                    cargarEmpresa(empresa);
                }
                if (!departamentoList.contains(departamento)) {
                    departamentoList.add(departamento);
                }
                if (!sedeList.contains(sede)) {
                    departamentoList.add(sede);
                }
                if (!ubicacionList.contains(ubicacion)) {
                    ubicacionList.add(ubicacion);
                }
                if (!codCatalogoList.contains(codCatalogo+" "+nomCatalogo+" "+empresa)) {
                    codCatalogoList.add(codCatalogo+" "+nomCatalogo+" "+empresa);
                    cargarCatalogo(codCatalogo,nomCatalogo, empresa);
                }
                */


                }


                //cargarCatalogo(catalogoList);

                activos.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            //time = time2;


            return null;
        }

    private void cargarDatos(String codigo, String barras, String empresa,
                             String departamento, String sede, String ubicacion,
                             String codCatalogo, String nomCatalogo, String placa,
                             String marca, String modelo, String serie, String foto,
                             String responsable, String tipoActivo, String activoPadre,
                             String codCentro, String centroCosto, String cuenta,
                             String estado, String expediente, String ordenCompra,
                             String factura, String fecCompra, String observacion) {

        Activo activo=new Activo();
        activo.setCodigo(codigo);
        activo.setCodigobarra(barras);
        activo.setEmpresa(empresa);
        activo.setDepartamento(departamento);
        activo.setSede(sede);
        activo.setUbicacion(ubicacion);
        activo.setCodcatalogo(codCatalogo);
        activo.setCatalogo(nomCatalogo);
        activo.setPlaca(placa);
        activo.setMarca(marca);
        activo.setModelo(modelo);
        activo.setSerie(serie);
        activo.setFoto(foto);
        activo.setResponsable(responsable);
        activo.setTipoActivo(tipoActivo);
        activo.setActivopadre(activoPadre);
        activo.setCodCentro(codCentro);
        activo.setCentro(centroCosto);
        activo.setCuenta(cuenta);
        activo.setEstado(estado);
        activo.setExpediente(expediente);
        activo.setOrdencompra(ordenCompra);
        activo.setFactura(factura);
        activo.setFechacompra(fecCompra);
        activo.setObservacion(observacion);

        Controller.getDaoSession().getActivoDao().insert(activo);


    }

    }


