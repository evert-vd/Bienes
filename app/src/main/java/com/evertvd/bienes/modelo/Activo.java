package com.evertvd.bienes.modelo;

import org.greenrobot.greendao.annotation.*;

import java.util.List;
import com.evertvd.bienes.modelo.dao.DaoSession;
import org.greenrobot.greendao.DaoException;

import com.evertvd.bienes.modelo.dao.ActivoDao;
import com.evertvd.bienes.modelo.dao.CatalogoDao;
import com.evertvd.bienes.modelo.dao.CentroCostoDao;
import com.evertvd.bienes.modelo.dao.CuentaContableDao;
import com.evertvd.bienes.modelo.dao.HistorialDao;
import com.evertvd.bienes.modelo.dao.ResponsableDao;
import com.evertvd.bienes.modelo.dao.UbicacionDao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "ACTIVO".
 */
@Entity(active = true)
public class Activo {

    @Id(autoincrement = true)
    private Long id;

    @Unique
    private String codigo;
    private String activopadre;
    private String codigobarra;
    private String descripcion;
    private String placa;
    private String marca;
    private String modelo;
    private String serie;
    private String tipo;
    private String expediente;
    private String ordencompra;
    private String factura;
    private String fechacompra;
    private String estado;

    @Index
    private Long ubicacion_id;

    @Index
    private Long responsable_id;

    @Index
    private Long cuentacontable_id;

    @Index
    private Long centrocosto_id;

    @Index
    private Long catalogo_id;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient ActivoDao myDao;

    @ToOne(joinProperty = "ubicacion_id")
    private Ubicacion ubicacion;

    @Generated
    private transient Long ubicacion__resolvedKey;

    @ToOne(joinProperty = "responsable_id")
    private Responsable responsable;

    @Generated
    private transient Long responsable__resolvedKey;

    @ToOne(joinProperty = "cuentacontable_id")
    private CuentaContable cuentaContable;

    @Generated
    private transient Long cuentaContable__resolvedKey;

    @ToOne(joinProperty = "centrocosto_id")
    private CentroCosto centroCosto;

    @Generated
    private transient Long centroCosto__resolvedKey;

    @ToOne(joinProperty = "catalogo_id")
    private Catalogo catalogo;

    @Generated
    private transient Long catalogo__resolvedKey;

    @ToMany(joinProperties = {
        @JoinProperty(name = "id", referencedName = "activo_id")
    })
    private List<Historial> historialList;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Activo() {
    }

    public Activo(Long id) {
        this.id = id;
    }

    @Generated
    public Activo(Long id, String codigo, String activopadre, String codigobarra, String descripcion, String placa, String marca, String modelo, String serie, String tipo, String expediente, String ordencompra, String factura, String fechacompra, String estado, Long ubicacion_id, Long responsable_id, Long cuentacontable_id, Long centrocosto_id, Long catalogo_id) {
        this.id = id;
        this.codigo = codigo;
        this.activopadre = activopadre;
        this.codigobarra = codigobarra;
        this.descripcion = descripcion;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.tipo = tipo;
        this.expediente = expediente;
        this.ordencompra = ordencompra;
        this.factura = factura;
        this.fechacompra = fechacompra;
        this.estado = estado;
        this.ubicacion_id = ubicacion_id;
        this.responsable_id = responsable_id;
        this.cuentacontable_id = cuentacontable_id;
        this.centrocosto_id = centrocosto_id;
        this.catalogo_id = catalogo_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getActivoDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getActivopadre() {
        return activopadre;
    }

    public void setActivopadre(String activopadre) {
        this.activopadre = activopadre;
    }

    public String getCodigobarra() {
        return codigobarra;
    }

    public void setCodigobarra(String codigobarra) {
        this.codigobarra = codigobarra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getOrdencompra() {
        return ordencompra;
    }

    public void setOrdencompra(String ordencompra) {
        this.ordencompra = ordencompra;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(String fechacompra) {
        this.fechacompra = fechacompra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getUbicacion_id() {
        return ubicacion_id;
    }

    public void setUbicacion_id(Long ubicacion_id) {
        this.ubicacion_id = ubicacion_id;
    }

    public Long getResponsable_id() {
        return responsable_id;
    }

    public void setResponsable_id(Long responsable_id) {
        this.responsable_id = responsable_id;
    }

    public Long getCuentacontable_id() {
        return cuentacontable_id;
    }

    public void setCuentacontable_id(Long cuentacontable_id) {
        this.cuentacontable_id = cuentacontable_id;
    }

    public Long getCentrocosto_id() {
        return centrocosto_id;
    }

    public void setCentrocosto_id(Long centrocosto_id) {
        this.centrocosto_id = centrocosto_id;
    }

    public Long getCatalogo_id() {
        return catalogo_id;
    }

    public void setCatalogo_id(Long catalogo_id) {
        this.catalogo_id = catalogo_id;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Ubicacion getUbicacion() {
        Long __key = this.ubicacion_id;
        if (ubicacion__resolvedKey == null || !ubicacion__resolvedKey.equals(__key)) {
            __throwIfDetached();
            UbicacionDao targetDao = daoSession.getUbicacionDao();
            Ubicacion ubicacionNew = targetDao.load(__key);
            synchronized (this) {
                ubicacion = ubicacionNew;
            	ubicacion__resolvedKey = __key;
            }
        }
        return ubicacion;
    }

    @Generated
    public void setUbicacion(Ubicacion ubicacion) {
        synchronized (this) {
            this.ubicacion = ubicacion;
            ubicacion_id = ubicacion == null ? null : ubicacion.getId();
            ubicacion__resolvedKey = ubicacion_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Responsable getResponsable() {
        Long __key = this.responsable_id;
        if (responsable__resolvedKey == null || !responsable__resolvedKey.equals(__key)) {
            __throwIfDetached();
            ResponsableDao targetDao = daoSession.getResponsableDao();
            Responsable responsableNew = targetDao.load(__key);
            synchronized (this) {
                responsable = responsableNew;
            	responsable__resolvedKey = __key;
            }
        }
        return responsable;
    }

    @Generated
    public void setResponsable(Responsable responsable) {
        synchronized (this) {
            this.responsable = responsable;
            responsable_id = responsable == null ? null : responsable.getId();
            responsable__resolvedKey = responsable_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public CuentaContable getCuentaContable() {
        Long __key = this.cuentacontable_id;
        if (cuentaContable__resolvedKey == null || !cuentaContable__resolvedKey.equals(__key)) {
            __throwIfDetached();
            CuentaContableDao targetDao = daoSession.getCuentaContableDao();
            CuentaContable cuentaContableNew = targetDao.load(__key);
            synchronized (this) {
                cuentaContable = cuentaContableNew;
            	cuentaContable__resolvedKey = __key;
            }
        }
        return cuentaContable;
    }

    @Generated
    public void setCuentaContable(CuentaContable cuentaContable) {
        synchronized (this) {
            this.cuentaContable = cuentaContable;
            cuentacontable_id = cuentaContable == null ? null : cuentaContable.getId();
            cuentaContable__resolvedKey = cuentacontable_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public CentroCosto getCentroCosto() {
        Long __key = this.centrocosto_id;
        if (centroCosto__resolvedKey == null || !centroCosto__resolvedKey.equals(__key)) {
            __throwIfDetached();
            CentroCostoDao targetDao = daoSession.getCentroCostoDao();
            CentroCosto centroCostoNew = targetDao.load(__key);
            synchronized (this) {
                centroCosto = centroCostoNew;
            	centroCosto__resolvedKey = __key;
            }
        }
        return centroCosto;
    }

    @Generated
    public void setCentroCosto(CentroCosto centroCosto) {
        synchronized (this) {
            this.centroCosto = centroCosto;
            centrocosto_id = centroCosto == null ? null : centroCosto.getId();
            centroCosto__resolvedKey = centrocosto_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Catalogo getCatalogo() {
        Long __key = this.catalogo_id;
        if (catalogo__resolvedKey == null || !catalogo__resolvedKey.equals(__key)) {
            __throwIfDetached();
            CatalogoDao targetDao = daoSession.getCatalogoDao();
            Catalogo catalogoNew = targetDao.load(__key);
            synchronized (this) {
                catalogo = catalogoNew;
            	catalogo__resolvedKey = __key;
            }
        }
        return catalogo;
    }

    @Generated
    public void setCatalogo(Catalogo catalogo) {
        synchronized (this) {
            this.catalogo = catalogo;
            catalogo_id = catalogo == null ? null : catalogo.getId();
            catalogo__resolvedKey = catalogo_id;
        }
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Historial> getHistorialList() {
        if (historialList == null) {
            __throwIfDetached();
            HistorialDao targetDao = daoSession.getHistorialDao();
            List<Historial> historialListNew = targetDao._queryActivo_HistorialList(id);
            synchronized (this) {
                if(historialList == null) {
                    historialList = historialListNew;
                }
            }
        }
        return historialList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetHistorialList() {
        historialList = null;
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
