package com.evertvd.bienes.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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
import com.evertvd.bienes.modelo.dao.DepartamentoDao;
import com.evertvd.bienes.vista.activitys.MainActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by evertvd on 18/09/2017.
 */

    public class HiloSecundario extends Thread {
    private String path;
    Context context;

    public HiloSecundario(Context context,String path) {
        this.path=path;
        this.context=context;
    }

        @Override
        public void run() {

            Long startTime = System.nanoTime();
            Total total=new Total();
            total.setTotal(0);
            total.setRuta(path);
            Controller.getDaoSession().getTotalDao().insert(total);
            leerArchivo();

            List<Activo> activos=Controller.getDaoSession().getActivoDao().loadAll();
            int tamaño=activos.size();
            total.setTotal(tamaño);
            Controller.getDaoSession().getTotalDao().update(total);
            long endTime = System.nanoTime();
            int time2 = (int) TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
            Log.e("hiloSec", String.valueOf(time2));

                try {
                    // Dejar libre la CPU durante
                    // unos milisegundos
                    Thread.sleep(100);
                    //context.getApplicationContext().startActivity();

                    context.startActivity(new Intent(context, MainActivity.class));
                    Intent intent=new Intent(context,MainActivity.class);
                    context.startActivity(intent);


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
                if(Buscar.buscarCatalogo(nomCatalogo)==null){
                    Catalogo catalogo=new Catalogo();
                    catalogo.setCatalogo(nomCatalogo);
                    catalogo.setCodigo(codCatalogo);
                    catalogo.setEmpresaId(Buscar.buscarEmpresa(empresa).getId());
                    Controller.getDaoSession().getCatalogoDao().insert(catalogo);
                }

                Activo activo = new Activo();
                activo.setCodigo(codigo);
                activo.setCodigobarra(barras);
                activo.setCatalogo_id(Buscar.buscarCatalogo(nomCatalogo).getId());
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
