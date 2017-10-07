package com.evertvd.bienes.utils;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.util.Log;
import android.widget.Toast;

import com.csvreader.CsvReader;

import com.evertvd.bienes.R;
import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;

import com.evertvd.bienes.modelo.ActivoAll;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.Departamento;

import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.CentroCostoDao;
import com.evertvd.bienes.modelo.dao.CuentaContableDao;
import com.evertvd.bienes.modelo.dao.DepartamentoDao;
import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.ResponsableDao;
import com.evertvd.bienes.modelo.dao.SedeDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;
import com.evertvd.bienes.vista.activitys.MainActivity;
import com.evertvd.bienes.vista.fragments.Principal;


import org.greenrobot.greendao.query.Query;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

public class TareaCarga  extends AsyncTask<Void, Void, Void> {

    ProgressDialog progress;
    //DialogFragment dialogTask;
    Context context;
    String path;
    private List<Empresa> empresaList;
    private List<ActivoAll> activoAlls;
    private List<Departamento> departamentoList;

    /*
    String empresa;
    String codigo ;
    String barras;
    String departamento ;
    String sede ;
    String ubicacion ;
    String codCatalogo ;
    String nomCatalogo;
    String placa ;
    String marca ;
    String modelo;
    String serie ;
    String foto ;
    String responsable ;
    String tipoActivo ;
    String activoPadre ;
    String codCentro ;
    String centroCosto ;
    String cuenta ;
    String estado ;
    String expediente;
    String ordenCompra;
    String factura ;
    String fecCompra;
    String observacion;
    */

    //para progresDialog
    public TareaCarga(ProgressDialog progress, Context context, String path) {
        this.progress = progress;
        this.context = context;
        this.path = path;
    }


    /*
    //para dialog fragment
    public TareaCarga(DialogFragment progress, Context context, String path) {
        this.dialogTask = progress;
        this.context = context;
        this.path=path;
    }
    */

    public void onPreExecute() {
        progress.show();

        //aquí se puede colocar código a ejecutarse previo
        //a la operación
        /*
        HiloEmpresa hiloEmpresa=new HiloEmpresa(path);
        HiloDepartamento hiloDepartamento=new HiloDepartamento(path);
        HiloCentroCosto hiloCentroCosto=new HiloCentroCosto(path);
        HiloResponsable hiloResponsable=new HiloResponsable(path);
        HiloCuentaContable hiloCuentaContable=new HiloCuentaContable(path);
        hiloEmpresa.start();
        hiloDepartamento.start();
        hiloCentroCosto.start();
        hiloResponsable.start();
        hiloCuentaContable.start();
        */
    }


    public void onPostExecute(Void unused) {
        //aquí se puede colocar código que
        //se ejecutará tras finalizar
        progress.dismiss();
        //context.startActivity(new Intent(context,MainActivity.class));


        //context.startActivity(new Intent(context, MainActivity.class));
        Toast.makeText(context, "Data Cargada correctamente", Toast.LENGTH_SHORT).show();
    }


    protected Void doInBackground(Void... params) {

        //realizar la operación aquí
        Long startTime = System.nanoTime();

        leerArchivo();

        long endTime = System.nanoTime();
        int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        Log.e("Tiempo ejecutado", String.valueOf(time2));

        return null;
    }


    private void leerArchivo() {

        try {

            CsvReader activos = new CsvReader(path);
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



                if(Buscar.buscarEmpresa(empresa)==null){
                    Empresa empresa1=new Empresa();
                    empresa1.setEmpresa(empresa);
                    Controller.getDaoSession().getEmpresaDao().insert(empresa1);
                }


                        if(Buscar.buscarDepartamento(departamento)==null){
                            Departamento departamento1=new Departamento();
                            departamento1.setDepartamento(departamento);
                            Controller.getDaoSession().getDepartamentoDao().insert(departamento1);
                        }



                        if(Buscar.buscarSede(sede)==null){
                            Sede sede1=new Sede();
                            sede1.setSede(sede);
                            sede1.setDepartamento_id(Buscar.buscarDepartamento(departamento).getId());
                            Controller.getDaoSession().getSedeDao().insert(sede1);
                        }
                        if(Buscar.buscarUbicacion(ubicacion)==null){
                            Ubicacion ubicacion1=new Ubicacion();
                            ubicacion1.setUbicacion(ubicacion);
                            ubicacion1.setSede_id(Buscar.buscarSede(sede).getId());
                            Controller.getDaoSession().getUbicacionDao().insert(ubicacion1);
                        }

                        if(Buscar.buscarResponsable(responsable)==null){
                            Responsable responsable1=new Responsable();
                            responsable1.setResponsable(responsable);
                            Controller.getDaoSession().getResponsableDao().insert(responsable1);
                        }

                        if(Buscar.buscarCentro(centroCosto)==null){
                            CentroCosto centroCosto1=new CentroCosto();
                            centroCosto1.setCentro(centroCosto);
                            centroCosto1.setCodigo(codCentro);
                            Controller.getDaoSession().getCentroCostoDao().insert(centroCosto1);
                        }
                        if(Buscar.buscarCuenta(cuenta)==null){
                            CuentaContable cuentaContable=new CuentaContable();
                            cuentaContable.setCodigo(cuenta);
                            Controller.getDaoSession().getCuentaContableDao().insert(cuentaContable);
                        }



                Activo activo = new Activo();
                activo.setCodigo(codigo);
                activo.setCodigobarra(barras);
                //setidCatalogo
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

                Controller.getDaoSession().getActivoDao().insert(activo);


            }

        activos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private long getIdEmpresa(String texto) {
        if (Buscar.buscarEmpresa(texto) == null) {
            Empresa empresa = new Empresa();
            empresa.setEmpresa(texto);
            Controller.getDaoSession().getEmpresaDao().insert(empresa);
        }
        Empresa empresa = Controller.getDaoSession().getEmpresaDao().queryBuilder().where(EmpresaDao.Properties.Empresa.eq(texto)).unique();
        return empresa.getId();
    }

    private long getIdDepartamento(String texto) {
        if (Buscar.buscarDepartamento(texto) == null) {
            Departamento departamento = new Departamento();
            departamento.setDepartamento(texto);
            Controller.getDaoSession().getDepartamentoDao().insert(departamento);
        }
        Departamento departamento = Controller.getDaoSession().getDepartamentoDao().queryBuilder().where(DepartamentoDao.Properties.Departamento.eq(texto)).unique();
        return departamento.getId();
    }



    private long getIdSede(String texto, String empresa, String departamento) {
        if (Buscar.buscarSede(texto) == null) {
            Sede sede = new Sede();
            sede.setSede(texto);
            sede.setDepartamento_id(getIdDepartamento(departamento));
            //sede.setDepartamento_has_empresa_id(getIdDepartamento_Has_Empresa(empresa, departamento));
            Controller.getDaoSession().getSedeDao().insert(sede);
        }
        Sede sede = Controller.getDaoSession().getSedeDao().queryBuilder().where(SedeDao.Properties.Sede.eq(texto)).unique();
        return sede.getId();
    }

    private long getIdUbicacion(String texto, String sede, String empresa, String departamento) {
        if (Buscar.buscarUbicacion(texto) == null) {
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setUbicacion(texto);
            ubicacion.setSede_id(getIdSede(sede, empresa, departamento));
            Controller.getDaoSession().getUbicacionDao().insert(ubicacion);

        }
        Ubicacion ubicacion = Controller.getDaoSession().getUbicacionDao().queryBuilder().where(UbicacionDao.Properties.Ubicacion.eq(texto)).unique();
        return ubicacion.getId();
    }

    private long getIdResponsable(String texto) {
        if (Buscar.buscarResponsable(texto) == null) {
            Responsable responsable = new Responsable();
            responsable.setResponsable(texto);
            Controller.getDaoSession().getResponsableDao().insert(responsable);
        }

        Responsable responsable = Controller.getDaoSession().getResponsableDao().queryBuilder().where(ResponsableDao.Properties.Responsable.eq(texto)).unique();
        return responsable.getId();
    }

    private long getIdCentroCosto(String texto, String codigo) {
        if (Buscar.buscarCentro(texto) == null) {
            CentroCosto centroCosto = new CentroCosto();
            centroCosto.setCodigo(codigo);
            centroCosto.setCentro(texto);
            Controller.getDaoSession().getCentroCostoDao().insert(centroCosto);
        }

        CentroCosto centroCosto = Controller.getDaoSession().getCentroCostoDao().queryBuilder().where(CentroCostoDao.Properties.Centro.eq(texto)).unique();
        return centroCosto.getId();
    }


    private long getIdCuenta(String texto) {
        if (Buscar.buscarCuenta(texto) == null) {
            CuentaContable cuentaContable = new CuentaContable();
            cuentaContable.setCodigo(texto);
            Controller.getDaoSession().getCuentaContableDao().insert(cuentaContable);
        }

        CuentaContable cuentaContable = Controller.getDaoSession().getCuentaContableDao().queryBuilder().where(CuentaContableDao.Properties.Codigo.eq(texto)).unique();
        return cuentaContable.getId();
    }


}