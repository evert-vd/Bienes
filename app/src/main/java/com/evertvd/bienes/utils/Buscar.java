package com.evertvd.bienes.utils;

import android.util.Log;

import com.evertvd.bienes.controlador.Controller;
import com.evertvd.bienes.modelo.Activo;

import com.evertvd.bienes.modelo.dao.ActivoDao;


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
        CentroCosto centroCosto = Controller.getDaoSession().getCentroCostoDao().queryBuilder().where(CentroCostoDao.Properties.Codigo.eq(texto)).unique();
        return centroCosto;
    }

    public static Catalogo buscarCatalogo(String nomEmpresa,String texto) {
        Empresa empresa=buscarEmpresa(nomEmpresa);
        Catalogo catalogo = Controller.getDaoSession().getCatalogoDao().queryBuilder().where(CatalogoDao.Properties.Codigo.eq(texto)).where(CatalogoDao.Properties.Empresa_id2.eq(empresa.getId())).unique();
        return catalogo;
    }

    public static Activo buscarBarras(String texto) {
        Activo activo = Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigobarra.eq(texto)).unique();
        return activo;
    }
    */

    public static List<Activo> buscarBarras(String texto) {
        List<Activo> activoList=Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigobarra.eq(texto)).list();
        //Activo activo = Controller.getDaoSession().getActivoDao().queryBuilder().where(ActivoDao.Properties.Codigobarra.eq(texto)).unique();
        for (int i=0;i<activoList.size();i++){
            Log.e("cb",activoList.get(i).getCodigobarra());
        }
        return activoList;
    }
}