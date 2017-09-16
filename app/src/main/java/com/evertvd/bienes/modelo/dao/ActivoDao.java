package com.evertvd.bienes.modelo.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.evertvd.bienes.modelo.Activo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ACTIVO".
*/
public class ActivoDao extends AbstractDao<Activo, Long> {

    public static final String TABLENAME = "ACTIVO";

    /**
     * Properties of entity Activo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Codigo = new Property(1, String.class, "codigo", false, "CODIGO");
        public final static Property Codigobarra = new Property(2, String.class, "codigobarra", false, "CODIGOBARRA");
        public final static Property Empresa = new Property(3, String.class, "empresa", false, "EMPRESA");
        public final static Property Departamento = new Property(4, String.class, "departamento", false, "DEPARTAMENTO");
        public final static Property Sede = new Property(5, String.class, "sede", false, "SEDE");
        public final static Property Ubicacion = new Property(6, String.class, "ubicacion", false, "UBICACION");
        public final static Property Familia = new Property(7, String.class, "familia", false, "FAMILIA");
        public final static Property Subfamilia = new Property(8, String.class, "subfamilia", false, "SUBFAMILIA");
        public final static Property Codcatalogo = new Property(9, String.class, "codcatalogo", false, "CODCATALOGO");
        public final static Property Catalogo = new Property(10, String.class, "catalogo", false, "CATALOGO");
        public final static Property Placa = new Property(11, String.class, "placa", false, "PLACA");
        public final static Property Marca = new Property(12, String.class, "marca", false, "MARCA");
        public final static Property Modelo = new Property(13, String.class, "modelo", false, "MODELO");
        public final static Property Serie = new Property(14, String.class, "serie", false, "SERIE");
        public final static Property Foto = new Property(15, String.class, "foto", false, "FOTO");
        public final static Property Responsable = new Property(16, String.class, "responsable", false, "RESPONSABLE");
        public final static Property TipoActivo = new Property(17, String.class, "tipoActivo", false, "TIPO_ACTIVO");
        public final static Property Activopadre = new Property(18, String.class, "activopadre", false, "ACTIVOPADRE");
        public final static Property CodCentro = new Property(19, String.class, "codCentro", false, "COD_CENTRO");
        public final static Property Centro = new Property(20, String.class, "centro", false, "CENTRO");
        public final static Property Cuenta = new Property(21, String.class, "cuenta", false, "CUENTA");
        public final static Property Estado = new Property(22, String.class, "estado", false, "ESTADO");
        public final static Property Expediente = new Property(23, String.class, "expediente", false, "EXPEDIENTE");
        public final static Property Ordencompra = new Property(24, String.class, "ordencompra", false, "ORDENCOMPRA");
        public final static Property Factura = new Property(25, String.class, "factura", false, "FACTURA");
        public final static Property Fechacompra = new Property(26, String.class, "fechacompra", false, "FECHACOMPRA");
        public final static Property Observacion = new Property(27, String.class, "observacion", false, "OBSERVACION");
        public final static Property Seleccionado = new Property(28, Integer.class, "seleccionado", false, "SELECCIONADO");
    }

    private DaoSession daoSession;


    public ActivoDao(DaoConfig config) {
        super(config);
    }
    
    public ActivoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACTIVO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CODIGO\" TEXT UNIQUE ," + // 1: codigo
                "\"CODIGOBARRA\" TEXT," + // 2: codigobarra
                "\"EMPRESA\" TEXT," + // 3: empresa
                "\"DEPARTAMENTO\" TEXT," + // 4: departamento
                "\"SEDE\" TEXT," + // 5: sede
                "\"UBICACION\" TEXT," + // 6: ubicacion
                "\"FAMILIA\" TEXT," + // 7: familia
                "\"SUBFAMILIA\" TEXT," + // 8: subfamilia
                "\"CODCATALOGO\" TEXT," + // 9: codcatalogo
                "\"CATALOGO\" TEXT," + // 10: catalogo
                "\"PLACA\" TEXT," + // 11: placa
                "\"MARCA\" TEXT," + // 12: marca
                "\"MODELO\" TEXT," + // 13: modelo
                "\"SERIE\" TEXT," + // 14: serie
                "\"FOTO\" TEXT," + // 15: foto
                "\"RESPONSABLE\" TEXT," + // 16: responsable
                "\"TIPO_ACTIVO\" TEXT," + // 17: tipoActivo
                "\"ACTIVOPADRE\" TEXT," + // 18: activopadre
                "\"COD_CENTRO\" TEXT," + // 19: codCentro
                "\"CENTRO\" TEXT," + // 20: centro
                "\"CUENTA\" TEXT," + // 21: cuenta
                "\"ESTADO\" TEXT," + // 22: estado
                "\"EXPEDIENTE\" TEXT," + // 23: expediente
                "\"ORDENCOMPRA\" TEXT," + // 24: ordencompra
                "\"FACTURA\" TEXT," + // 25: factura
                "\"FECHACOMPRA\" TEXT," + // 26: fechacompra
                "\"OBSERVACION\" TEXT," + // 27: observacion
                "\"SELECCIONADO\" INTEGER);"); // 28: seleccionado
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACTIVO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Activo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String codigo = entity.getCodigo();
        if (codigo != null) {
            stmt.bindString(2, codigo);
        }
 
        String codigobarra = entity.getCodigobarra();
        if (codigobarra != null) {
            stmt.bindString(3, codigobarra);
        }
 
        String empresa = entity.getEmpresa();
        if (empresa != null) {
            stmt.bindString(4, empresa);
        }
 
        String departamento = entity.getDepartamento();
        if (departamento != null) {
            stmt.bindString(5, departamento);
        }
 
        String sede = entity.getSede();
        if (sede != null) {
            stmt.bindString(6, sede);
        }
 
        String ubicacion = entity.getUbicacion();
        if (ubicacion != null) {
            stmt.bindString(7, ubicacion);
        }
 
        String familia = entity.getFamilia();
        if (familia != null) {
            stmt.bindString(8, familia);
        }
 
        String subfamilia = entity.getSubfamilia();
        if (subfamilia != null) {
            stmt.bindString(9, subfamilia);
        }
 
        String codcatalogo = entity.getCodcatalogo();
        if (codcatalogo != null) {
            stmt.bindString(10, codcatalogo);
        }
 
        String catalogo = entity.getCatalogo();
        if (catalogo != null) {
            stmt.bindString(11, catalogo);
        }
 
        String placa = entity.getPlaca();
        if (placa != null) {
            stmt.bindString(12, placa);
        }
 
        String marca = entity.getMarca();
        if (marca != null) {
            stmt.bindString(13, marca);
        }
 
        String modelo = entity.getModelo();
        if (modelo != null) {
            stmt.bindString(14, modelo);
        }
 
        String serie = entity.getSerie();
        if (serie != null) {
            stmt.bindString(15, serie);
        }
 
        String foto = entity.getFoto();
        if (foto != null) {
            stmt.bindString(16, foto);
        }
 
        String responsable = entity.getResponsable();
        if (responsable != null) {
            stmt.bindString(17, responsable);
        }
 
        String tipoActivo = entity.getTipoActivo();
        if (tipoActivo != null) {
            stmt.bindString(18, tipoActivo);
        }
 
        String activopadre = entity.getActivopadre();
        if (activopadre != null) {
            stmt.bindString(19, activopadre);
        }
 
        String codCentro = entity.getCodCentro();
        if (codCentro != null) {
            stmt.bindString(20, codCentro);
        }
 
        String centro = entity.getCentro();
        if (centro != null) {
            stmt.bindString(21, centro);
        }
 
        String cuenta = entity.getCuenta();
        if (cuenta != null) {
            stmt.bindString(22, cuenta);
        }
 
        String estado = entity.getEstado();
        if (estado != null) {
            stmt.bindString(23, estado);
        }
 
        String expediente = entity.getExpediente();
        if (expediente != null) {
            stmt.bindString(24, expediente);
        }
 
        String ordencompra = entity.getOrdencompra();
        if (ordencompra != null) {
            stmt.bindString(25, ordencompra);
        }
 
        String factura = entity.getFactura();
        if (factura != null) {
            stmt.bindString(26, factura);
        }
 
        String fechacompra = entity.getFechacompra();
        if (fechacompra != null) {
            stmt.bindString(27, fechacompra);
        }
 
        String observacion = entity.getObservacion();
        if (observacion != null) {
            stmt.bindString(28, observacion);
        }
 
        Integer seleccionado = entity.getSeleccionado();
        if (seleccionado != null) {
            stmt.bindLong(29, seleccionado);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Activo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String codigo = entity.getCodigo();
        if (codigo != null) {
            stmt.bindString(2, codigo);
        }
 
        String codigobarra = entity.getCodigobarra();
        if (codigobarra != null) {
            stmt.bindString(3, codigobarra);
        }
 
        String empresa = entity.getEmpresa();
        if (empresa != null) {
            stmt.bindString(4, empresa);
        }
 
        String departamento = entity.getDepartamento();
        if (departamento != null) {
            stmt.bindString(5, departamento);
        }
 
        String sede = entity.getSede();
        if (sede != null) {
            stmt.bindString(6, sede);
        }
 
        String ubicacion = entity.getUbicacion();
        if (ubicacion != null) {
            stmt.bindString(7, ubicacion);
        }
 
        String familia = entity.getFamilia();
        if (familia != null) {
            stmt.bindString(8, familia);
        }
 
        String subfamilia = entity.getSubfamilia();
        if (subfamilia != null) {
            stmt.bindString(9, subfamilia);
        }
 
        String codcatalogo = entity.getCodcatalogo();
        if (codcatalogo != null) {
            stmt.bindString(10, codcatalogo);
        }
 
        String catalogo = entity.getCatalogo();
        if (catalogo != null) {
            stmt.bindString(11, catalogo);
        }
 
        String placa = entity.getPlaca();
        if (placa != null) {
            stmt.bindString(12, placa);
        }
 
        String marca = entity.getMarca();
        if (marca != null) {
            stmt.bindString(13, marca);
        }
 
        String modelo = entity.getModelo();
        if (modelo != null) {
            stmt.bindString(14, modelo);
        }
 
        String serie = entity.getSerie();
        if (serie != null) {
            stmt.bindString(15, serie);
        }
 
        String foto = entity.getFoto();
        if (foto != null) {
            stmt.bindString(16, foto);
        }
 
        String responsable = entity.getResponsable();
        if (responsable != null) {
            stmt.bindString(17, responsable);
        }
 
        String tipoActivo = entity.getTipoActivo();
        if (tipoActivo != null) {
            stmt.bindString(18, tipoActivo);
        }
 
        String activopadre = entity.getActivopadre();
        if (activopadre != null) {
            stmt.bindString(19, activopadre);
        }
 
        String codCentro = entity.getCodCentro();
        if (codCentro != null) {
            stmt.bindString(20, codCentro);
        }
 
        String centro = entity.getCentro();
        if (centro != null) {
            stmt.bindString(21, centro);
        }
 
        String cuenta = entity.getCuenta();
        if (cuenta != null) {
            stmt.bindString(22, cuenta);
        }
 
        String estado = entity.getEstado();
        if (estado != null) {
            stmt.bindString(23, estado);
        }
 
        String expediente = entity.getExpediente();
        if (expediente != null) {
            stmt.bindString(24, expediente);
        }
 
        String ordencompra = entity.getOrdencompra();
        if (ordencompra != null) {
            stmt.bindString(25, ordencompra);
        }
 
        String factura = entity.getFactura();
        if (factura != null) {
            stmt.bindString(26, factura);
        }
 
        String fechacompra = entity.getFechacompra();
        if (fechacompra != null) {
            stmt.bindString(27, fechacompra);
        }
 
        String observacion = entity.getObservacion();
        if (observacion != null) {
            stmt.bindString(28, observacion);
        }
 
        Integer seleccionado = entity.getSeleccionado();
        if (seleccionado != null) {
            stmt.bindLong(29, seleccionado);
        }
    }

    @Override
    protected final void attachEntity(Activo entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Activo readEntity(Cursor cursor, int offset) {
        Activo entity = new Activo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // codigo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // codigobarra
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // empresa
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // departamento
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sede
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // ubicacion
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // familia
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // subfamilia
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // codcatalogo
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // catalogo
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // placa
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // marca
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // modelo
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // serie
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // foto
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // responsable
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // tipoActivo
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // activopadre
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // codCentro
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // centro
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // cuenta
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // estado
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // expediente
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // ordencompra
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // factura
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // fechacompra
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // observacion
            cursor.isNull(offset + 28) ? null : cursor.getInt(offset + 28) // seleccionado
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Activo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCodigo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCodigobarra(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEmpresa(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDepartamento(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSede(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUbicacion(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFamilia(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSubfamilia(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCodcatalogo(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCatalogo(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPlaca(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setMarca(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setModelo(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setSerie(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setFoto(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setResponsable(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setTipoActivo(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setActivopadre(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setCodCentro(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setCentro(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setCuenta(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setEstado(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setExpediente(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setOrdencompra(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setFactura(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setFechacompra(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setObservacion(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setSeleccionado(cursor.isNull(offset + 28) ? null : cursor.getInt(offset + 28));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Activo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Activo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Activo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
