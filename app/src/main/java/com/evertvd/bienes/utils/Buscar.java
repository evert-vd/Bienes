package com.evertvd.bienes.utils;

import android.util.Log;

import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.Departamento;

import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Historial;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.evertvd.bienes.modelo.dao.CentroCostoDao;
import com.evertvd.bienes.modelo.dao.CuentaContableDao;
import com.evertvd.bienes.modelo.dao.DepartamentoDao;

import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.HistorialDao;
import com.evertvd.bienes.modelo.dao.ResponsableDao;
import com.evertvd.bienes.modelo.dao.SedeDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;

import java.util.List;

/**
 * Created by evertvd on 14/09/2017.
 */

public class Buscar {

    public static Empresa buscarEmpresa(String texto){
        Empresa empresa = Controller.getDaoSession().getEmpresaDao().queryBuilder().where(EmpresaDao.Properties.Empresa.eq(texto)).unique();
       return empresa;
         }

    public static Departamento buscarDepartamento(String texto) {
        Departamento departamento = Controller.getDaoSession().getDepartamentoDao().queryBuilder().where(DepartamentoDao.Properties.Departamento.eq(texto)).unique();
        return departamento;
    }


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

    public static Catalogo buscarCatalogo(String catalogo, String empresa) {
        Empresa empresa1=Buscar.buscarEmpresa(empresa);
        Catalogo catalogo1 = Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.Catalogo.eq(catalogo)).where(CatalogoDao.Properties.Empresa_id.eq(empresa1.getId())).unique();
        return catalogo1;
    }



    public static List<Activo> buscarBarras(String texto) {
        List<Activo> activoList=Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigobarra.eq(texto)).list();
        //Activo activo = Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigobarra.eq(texto)).unique();
        return activoList;
    }

    public static Historial buscarHistorial(String historial, long idActivo){
        Historial historial1=Controller.getDaoSession().getHistorialDao().queryBuilder().where(HistorialDao.Properties.Campo_modificado.eq(historial)).where(HistorialDao.Properties.Activo_id.eq(idActivo)).unique();
        return historial1;
    }
}