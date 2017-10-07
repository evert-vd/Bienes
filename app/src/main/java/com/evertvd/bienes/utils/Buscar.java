package com.evertvd.bienes.utils;

import android.util.Log;

import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.Departamento;

import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.evertvd.bienes.modelo.dao.CentroCostoDao;
import com.evertvd.bienes.modelo.dao.CuentaContableDao;
import com.evertvd.bienes.modelo.dao.DepartamentoDao;

import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.ResponsableDao;
import com.evertvd.bienes.modelo.dao.SedeDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;

import java.util.List;

/**
 * Created by evertvd on 14/09/2017.
 */

public class Buscar {

    /*
    public static Empresa buscarEmpresa(String texto){
        //List<Empresa> empresaList=Controller.getDaoSession().getEmpresaDao().queryBuilder().where(EmpresaDao.Properties.Empresa.eq(texto)).list();
       // if(empresaList.isEmpty()){
            //return null;
        //}else{
        Empresa empresa = Controller.getDaoSession().getEmpresaDao().queryBuilder().where(EmpresaDao.Properties.Id.eq(texto)).unique();
            if(empresa!=null){
               return empresa;
            }else{
                return null;
            }
       // }
    }

    public static Departamento buscarDepartamento(String texto) {
        Departamento departamento = Controller.getDaoSession().getDepartamentoDao().queryBuilder().where(DepartamentoDao.Properties.Departamento.eq(texto)).unique();
        return departamento;
    }






    public static Activo buscarBarras(String texto) {
        Activo activo = Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigobarra.eq(texto)).unique();
        return activo;
    }
    */

    public static Empresa buscarEmpresa(String texto){
        Empresa empresa = Controller.getDaoSession().getEmpresaDao().queryBuilder().where(EmpresaDao.Properties.Empresa.eq(texto)).unique();
       return empresa;
         }

    public static Departamento buscarDepartamento(String texto) {
        Departamento departamento = Controller.getDaoSession().getDepartamentoDao().queryBuilder().where(DepartamentoDao.Properties.Departamento.eq(texto)).unique();
        return departamento;
    }

    /*
    public static Departamento_Has_Empresa buscarDepartamentoHasEmpresa(long idEmpresa, long idDepartamento) {
        Departamento_Has_Empresa departamento_has_empresa = Controller.getDaoSession().getDepartamento_Has_EmpresaDao().queryBuilder().where(Departamento_Has_EmpresaDao.Properties.Departamento_id.eq(idDepartamento))
                .where(Departamento_Has_EmpresaDao.Properties.Empresa_id.eq(idEmpresa)).unique();
        return departamento_has_empresa;
    }
    */

    public static Sede buscarSede(String texto) {
        Sede sede = Controller.getDaoSession().getSedeDao().queryBuilder().where(SedeDao.Properties.Sede.eq(texto)).unique();
        return sede;
    }

    public static Ubicacion buscarUbicacion(String texto) {
        Ubicacion ubicacion = Controller.getDaoSession().getUbicacionDao().queryBuilder().where(UbicacionDao.Properties.Ubicacion.eq(texto)).unique();
        return ubicacion;
    }

    public static Responsable buscarResponsable(String texto) {
        Responsable responsable = Controller.getDaoSession().getResponsableDao().queryBuilder().where(ResponsableDao.Properties.Responsable.eq(texto)).unique();
        return responsable;
    }

    public static CuentaContable buscarCuenta(String texto) {
        CuentaContable cuentaContable = Controller.getDaoSession().getCuentaContableDao().queryBuilder().where(CuentaContableDao.Properties.Codigo.eq(texto)).unique();
        return cuentaContable;
    }

    public static CentroCosto buscarCentro(String texto) {
        CentroCosto centroCosto = Controller.getDaoSession().getCentroCostoDao().queryBuilder().where(CentroCostoDao.Properties.Centro.eq(texto)).unique();
        return centroCosto;
    }

    public static Catalogo buscarCatalogo(String texto) {
        Catalogo catalogo = Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.Catalogo.eq(texto)).unique();
        return catalogo;
    }



    public static List<Activo> buscarBarras(String texto) {
        List<Activo> activoList=Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigobarra.eq(texto)).list();
        //Activo activo = Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigobarra.eq(texto)).unique();
        for (int i=0;i<activoList.size();i++){
            Log.e("cb",activoList.get(i).getCodigobarra()+" ubic:"+activoList.get(i).getUbicacion().getUbicacion());
        }
        return activoList;
    }


}