package com.evertvd.bienes.modelo;

import org.greenrobot.greendao.annotation.*;

import java.util.List;
import com.evertvd.bienes.modelo.dao.DaoSession;
import org.greenrobot.greendao.DaoException;

import com.evertvd.bienes.modelo.dao.DepartamentoDao;
import com.evertvd.bienes.modelo.dao.EmpresaDao;
import com.evertvd.bienes.modelo.dao.SedeDao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "DEPARTAMENTO".
 */
@Entity(active = true)
public class Departamento {

    @Id(autoincrement = true)
    private Long id;
    private String departamento;

    @Index
    private Long empresa_id;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient DepartamentoDao myDao;

    @ToOne(joinProperty = "empresa_id")
    private Empresa empresa;

    @Generated
    private transient Long empresa__resolvedKey;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "departamento_id")
    })
    private List<Sede> sedeList;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Departamento() {
    }

    public Departamento(Long id) {
        this.id = id;
    }

    @Generated
    public Departamento(Long id, String departamento, Long empresa_id) {
        this.id = id;
        this.departamento = departamento;
        this.empresa_id = empresa_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDepartamentoDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Long getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(Long empresa_id) {
        this.empresa_id = empresa_id;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Empresa getEmpresa() {
        Long __key = this.empresa_id;
        if (empresa__resolvedKey == null || !empresa__resolvedKey.equals(__key)) {
            __throwIfDetached();
            EmpresaDao targetDao = daoSession.getEmpresaDao();
            Empresa empresaNew = targetDao.load(__key);
            synchronized (this) {
                empresa = empresaNew;
            	empresa__resolvedKey = __key;
            }
        }
        return empresa;
    }

    @Generated
    public void setEmpresa(Empresa empresa) {
        synchronized (this) {
            this.empresa = empresa;
            empresa_id = empresa == null ? null : empresa.getId();
            empresa__resolvedKey = empresa_id;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Sede> getSedeList() {
        if (sedeList == null) {
            __throwIfDetached();
            SedeDao targetDao = daoSession.getSedeDao();
            List<Sede> sedeListNew = targetDao._queryDepartamento_SedeList(id);
            synchronized (this) {
                if(sedeList == null) {
                    sedeList = sedeListNew;
                }
            }
        }
        return sedeList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetSedeList() {
        sedeList = null;
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
