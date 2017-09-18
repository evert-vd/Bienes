package com.evertvd.bienes.controlador;

import android.app.Application;


import com.evertvd.bienes.modelo.dao.ActivoDao;

import com.evertvd.bienes.modelo.dao.DaoMaster;
import com.evertvd.bienes.modelo.dao.DaoSession;

import com.evertvd.bienes.modelo.dao.HistorialDao;


import org.greenrobot.greendao.database.Database;

/**
 * Created by evertvd on 17/07/2017.
 */

public class Controller extends Application {
    public static final boolean ENCRYPTED = true;
    static DaoSession daoSession;
    static ActivoDao activoDao;

    static HistorialDao historialDao;


    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "activosBD", null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        activoDao=daoSession.getActivoDao();
        //catalogoDao=daoSession.getCatalogoDao();
        //centroCostoDao=daoSession.getCentroCostoDao();
        //cuentaContableDao=daoSession.getCuentaContableDao();
        //departamentoDao=daoSession.getDepartamentoDao();
        //empresaDao = daoSession.getEmpresaDao();
        //historialDao=daoSession.getHistorialDao();
        //responsableDao=daoSession.getResponsableDao();
        //sedeDao=daoSession.getSedeDao();
        //ubicacionDao=daoSession.getUbicacionDao();

        ///// Using the below lines of code we can toggle ENCRYPTED to true or false in other to use either an encrypted database or not.
//      DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "users-db-encrypted" : "users-db");
//      Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//      daoSession = new DaoMaster(db).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
