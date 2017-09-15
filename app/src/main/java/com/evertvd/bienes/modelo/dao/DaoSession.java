package com.evertvd.bienes.modelo.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.evertvd.bienes.modelo.Empresa;
import com.evertvd.bienes.modelo.Departamento;
import com.evertvd.bienes.modelo.Sede;
import com.evertvd.bienes.modelo.Ubicacion;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.Activo;
import com.evertvd.bienes.modelo.Historial;

import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.DepartamentoDao;
import com.evertvd.bienes.modelo.dao.SedeDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;
import com.evertvd.bienes.modelo.dao.ResponsableDao;
import com.evertvd.bienes.modelo.dao.CuentaContableDao;
import com.evertvd.bienes.modelo.dao.CentroCostoDao;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.HistorialDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig empresaDaoConfig;
    private final DaoConfig departamentoDaoConfig;
    private final DaoConfig sedeDaoConfig;
    private final DaoConfig ubicacionDaoConfig;
    private final DaoConfig responsableDaoConfig;
    private final DaoConfig cuentaContableDaoConfig;
    private final DaoConfig centroCostoDaoConfig;
    private final DaoConfig catalogoDaoConfig;
    private final DaoConfig activoDaoConfig;
    private final DaoConfig historialDaoConfig;

    private final EmpresaDao empresaDao;
    private final DepartamentoDao departamentoDao;
    private final SedeDao sedeDao;
    private final UbicacionDao ubicacionDao;
    private final ResponsableDao responsableDao;
    private final CuentaContableDao cuentaContableDao;
    private final CentroCostoDao centroCostoDao;
    private final CatalogoDao catalogoDao;
    private final ActivoDao activoDao;
    private final HistorialDao historialDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        empresaDaoConfig = daoConfigMap.get(EmpresaDao.class).clone();
        empresaDaoConfig.initIdentityScope(type);

        departamentoDaoConfig = daoConfigMap.get(DepartamentoDao.class).clone();
        departamentoDaoConfig.initIdentityScope(type);

        sedeDaoConfig = daoConfigMap.get(SedeDao.class).clone();
        sedeDaoConfig.initIdentityScope(type);

        ubicacionDaoConfig = daoConfigMap.get(UbicacionDao.class).clone();
        ubicacionDaoConfig.initIdentityScope(type);

        responsableDaoConfig = daoConfigMap.get(ResponsableDao.class).clone();
        responsableDaoConfig.initIdentityScope(type);

        cuentaContableDaoConfig = daoConfigMap.get(CuentaContableDao.class).clone();
        cuentaContableDaoConfig.initIdentityScope(type);

        centroCostoDaoConfig = daoConfigMap.get(CentroCostoDao.class).clone();
        centroCostoDaoConfig.initIdentityScope(type);

        catalogoDaoConfig = daoConfigMap.get(CatalogoDao.class).clone();
        catalogoDaoConfig.initIdentityScope(type);

        activoDaoConfig = daoConfigMap.get(ActivoDao.class).clone();
        activoDaoConfig.initIdentityScope(type);

        historialDaoConfig = daoConfigMap.get(HistorialDao.class).clone();
        historialDaoConfig.initIdentityScope(type);

        empresaDao = new EmpresaDao(empresaDaoConfig, this);
        departamentoDao = new DepartamentoDao(departamentoDaoConfig, this);
        sedeDao = new SedeDao(sedeDaoConfig, this);
        ubicacionDao = new UbicacionDao(ubicacionDaoConfig, this);
        responsableDao = new ResponsableDao(responsableDaoConfig, this);
        cuentaContableDao = new CuentaContableDao(cuentaContableDaoConfig, this);
        centroCostoDao = new CentroCostoDao(centroCostoDaoConfig, this);
        catalogoDao = new CatalogoDao(catalogoDaoConfig, this);
        activoDao = new ActivoDao(activoDaoConfig, this);
        historialDao = new HistorialDao(historialDaoConfig, this);

        registerDao(Empresa.class, empresaDao);
        registerDao(Departamento.class, departamentoDao);
        registerDao(Sede.class, sedeDao);
        registerDao(Ubicacion.class, ubicacionDao);
        registerDao(Responsable.class, responsableDao);
        registerDao(CuentaContable.class, cuentaContableDao);
        registerDao(CentroCosto.class, centroCostoDao);
        registerDao(Catalogo.class, catalogoDao);
        registerDao(Activo.class, activoDao);
        registerDao(Historial.class, historialDao);
    }
    
    public void clear() {
        empresaDaoConfig.clearIdentityScope();
        departamentoDaoConfig.clearIdentityScope();
        sedeDaoConfig.clearIdentityScope();
        ubicacionDaoConfig.clearIdentityScope();
        responsableDaoConfig.clearIdentityScope();
        cuentaContableDaoConfig.clearIdentityScope();
        centroCostoDaoConfig.clearIdentityScope();
        catalogoDaoConfig.clearIdentityScope();
        activoDaoConfig.clearIdentityScope();
        historialDaoConfig.clearIdentityScope();
    }

    public EmpresaDao getEmpresaDao() {
        return empresaDao;
    }

    public DepartamentoDao getDepartamentoDao() {
        return departamentoDao;
    }

    public SedeDao getSedeDao() {
        return sedeDao;
    }

    public UbicacionDao getUbicacionDao() {
        return ubicacionDao;
    }

    public ResponsableDao getResponsableDao() {
        return responsableDao;
    }

    public CuentaContableDao getCuentaContableDao() {
        return cuentaContableDao;
    }

    public CentroCostoDao getCentroCostoDao() {
        return centroCostoDao;
    }

    public CatalogoDao getCatalogoDao() {
        return catalogoDao;
    }

    public ActivoDao getActivoDao() {
        return activoDao;
    }

    public HistorialDao getHistorialDao() {
        return historialDao;
    }

}
