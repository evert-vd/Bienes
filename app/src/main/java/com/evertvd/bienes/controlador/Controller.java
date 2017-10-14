package com.evertvd.bienes.controlador;

import android.app.Application;



import com.evertvd.bienes.modelo.dao.ActivoAllDao;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.evertvd.bienes.modelo.dao.CentroCostoDao;
import com.evertvd.bienes.modelo.dao.CuentaContableDao;
import com.evertvd.bienes.modelo.dao.DaoMaster;
import com.evertvd.bienes.modelo.dao.DaoSession;

import com.evertvd.bienes.modelo.dao.DepartamentoDao;

import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.HistorialDao;
import com.evertvd.bienes.modelo.dao.ResponsableDao;
import com.evertvd.bienes.modelo.dao.SedeDao;
import com.evertvd.bienes.modelo.dao.TotalDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;


import org.greenrobot.greendao.database.Database;

/**
 * Created by evertvd on 17/07/2017.
 */

public class Controller extends Application {
    public static final boolean ENCRYPTED = true;
    static TotalDao totalDao;
    static DaoSession daoSession;
    static ActivoDao activoDao;
    static ActivoAllDao activoAllDao;
    static CatalogoDao catalogoDao;
    static CentroCostoDao centroCostoDao;
    static CuentaContableDao cuentaContableDao;
    static DepartamentoDao departamentoDao;
    static EmpresaDao empresaDao;
    //static Departamento_Has_EmpresaDao departamento_has_empresaDao;
    static ResponsableDao responsableDao;
    static SedeDao sedeDao;
    static UbicacionDao ubicacionDao;
    static HistorialDao historialDao;


    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "activosBD", null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        totalDao=daoSession.getTotalDao();
        activoDao=daoSession.getActivoDao();

        catalogoDao=daoSession.getCatalogoDao();
        centroCostoDao=daoSession.getCentroCostoDao();
        cuentaContableDao=daoSession.getCuentaContableDao();
        departamentoDao=daoSession.getDepartamentoDao();
        empresaDao = daoSession.getEmpresaDao();
        //departamento_has_empresaDao=daoSession.getDepartamento_Has_EmpresaDao();
        historialDao=daoSession.getHistorialDao();
        responsableDao=daoSession.getResponsableDao();
        sedeDao=daoSession.getSedeDao();
        ubicacionDao=daoSession.getUbicacionDao();

        ///// Using the below lines of code we can toggle ENCRYPTED to true or false in other to use either an encrypted database or not.
//      DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "users-db-encrypted" : "users-db");
//      Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//      daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
