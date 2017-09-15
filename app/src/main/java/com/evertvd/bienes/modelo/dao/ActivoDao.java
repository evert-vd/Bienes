package com.evertvd.bienes.modelo.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.evertvd.bienes.modelo.Catalogo;
import com.evertvd.bienes.modelo.CentroCosto;
import com.evertvd.bienes.modelo.CuentaContable;
import com.evertvd.bienes.modelo.Responsable;
import com.evertvd.bienes.modelo.Ubicacion;

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
        public final static Property Activopadre = new Property(2, String.class, "activopadre", false, "ACTIVOPADRE");
        public final static Property Codigobarra = new Property(3, String.class, "codigobarra", false, "CODIGOBARRA");
        public final static Property Descripcion = new Property(4, String.class, "descripcion", false, "DESCRIPCION");
        public final static Property Placa = new Property(5, String.class, "placa", false, "PLACA");
        public final static Property Marca = new Property(6, String.class, "marca", false, "MARCA");
        public final static Property Modelo = new Property(7, String.class, "modelo", false, "MODELO");
        public final static Property Serie = new Property(8, String.class, "serie", false, "SERIE");
        public final static Property Tipo = new Property(9, String.class, "tipo", false, "TIPO");
        public final static Property Expediente = new Property(10, String.class, "expediente", false, "EXPEDIENTE");
        public final static Property Ordencompra = new Property(11, String.class, "ordencompra", false, "ORDENCOMPRA");
        public final static Property Factura = new Property(12, String.class, "factura", false, "FACTURA");
        public final static Property Fechacompra = new Property(13, String.class, "fechacompra", false, "FECHACOMPRA");
        public final static Property Estado = new Property(14, String.class, "estado", false, "ESTADO");
        public final static Property Ubicacion_id = new Property(15, Long.class, "ubicacion_id", false, "UBICACION_ID");
        public final static Property Responsable_id = new Property(16, Long.class, "responsable_id", false, "RESPONSABLE_ID");
        public final static Property Cuentacontable_id = new Property(17, Long.class, "cuentacontable_id", false, "CUENTACONTABLE_ID");
        public final static Property Centrocosto_id = new Property(18, Long.class, "centrocosto_id", false, "CENTROCOSTO_ID");
        public final static Property Catalogo_id = new Property(19, Long.class, "catalogo_id", false, "CATALOGO_ID");
    }

    private DaoSession daoSession;

    private Query<Activo> ubicacion_ActivoListQuery;
    private Query<Activo> responsable_ActivoListQuery;
    private Query<Activo> cuentaContable_ActivoListQuery;
    private Query<Activo> centroCosto_ActivoListQuery;
    private Query<Activo> catalogo_ActivoListQuery;

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
                "\"ACTIVOPADRE\" TEXT," + // 2: activopadre
                "\"CODIGOBARRA\" TEXT," + // 3: codigobarra
                "\"DESCRIPCION\" TEXT," + // 4: descripcion
                "\"PLACA\" TEXT," + // 5: placa
                "\"MARCA\" TEXT," + // 6: marca
                "\"MODELO\" TEXT," + // 7: modelo
                "\"SERIE\" TEXT," + // 8: serie
                "\"TIPO\" TEXT," + // 9: tipo
                "\"EXPEDIENTE\" TEXT," + // 10: expediente
                "\"ORDENCOMPRA\" TEXT," + // 11: ordencompra
                "\"FACTURA\" TEXT," + // 12: factura
                "\"FECHACOMPRA\" TEXT," + // 13: fechacompra
                "\"ESTADO\" TEXT," + // 14: estado
                "\"UBICACION_ID\" INTEGER," + // 15: ubicacion_id
                "\"RESPONSABLE_ID\" INTEGER," + // 16: responsable_id
                "\"CUENTACONTABLE_ID\" INTEGER," + // 17: cuentacontable_id
                "\"CENTROCOSTO_ID\" INTEGER," + // 18: centrocosto_id
                "\"CATALOGO_ID\" INTEGER);"); // 19: catalogo_id
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_ACTIVO_UBICACION_ID ON ACTIVO" +
                " (\"UBICACION_ID\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_ACTIVO_RESPONSABLE_ID ON ACTIVO" +
                " (\"RESPONSABLE_ID\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_ACTIVO_CUENTACONTABLE_ID ON ACTIVO" +
                " (\"CUENTACONTABLE_ID\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_ACTIVO_CENTROCOSTO_ID ON ACTIVO" +
                " (\"CENTROCOSTO_ID\");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_ACTIVO_CATALOGO_ID ON ACTIVO" +
                " (\"CATALOGO_ID\");");
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
 
        String activopadre = entity.getActivopadre();
        if (activopadre != null) {
            stmt.bindString(3, activopadre);
        }
 
        String codigobarra = entity.getCodigobarra();
        if (codigobarra != null) {
            stmt.bindString(4, codigobarra);
        }
 
        String descripcion = entity.getDescripcion();
        if (descripcion != null) {
            stmt.bindString(5, descripcion);
        }
 
        String placa = entity.getPlaca();
        if (placa != null) {
            stmt.bindString(6, placa);
        }
 
        String marca = entity.getMarca();
        if (marca != null) {
            stmt.bindString(7, marca);
        }
 
        String modelo = entity.getModelo();
        if (modelo != null) {
            stmt.bindString(8, modelo);
        }
 
        String serie = entity.getSerie();
        if (serie != null) {
            stmt.bindString(9, serie);
        }
 
        String tipo = entity.getTipo();
        if (tipo != null) {
            stmt.bindString(10, tipo);
        }
 
        String expediente = entity.getExpediente();
        if (expediente != null) {
            stmt.bindString(11, expediente);
        }
 
        String ordencompra = entity.getOrdencompra();
        if (ordencompra != null) {
            stmt.bindString(12, ordencompra);
        }
 
        String factura = entity.getFactura();
        if (factura != null) {
            stmt.bindString(13, factura);
        }
 
        String fechacompra = entity.getFechacompra();
        if (fechacompra != null) {
            stmt.bindString(14, fechacompra);
        }
 
        String estado = entity.getEstado();
        if (estado != null) {
            stmt.bindString(15, estado);
        }
 
        Long ubicacion_id = entity.getUbicacion_id();
        if (ubicacion_id != null) {
            stmt.bindLong(16, ubicacion_id);
        }
 
        Long responsable_id = entity.getResponsable_id();
        if (responsable_id != null) {
            stmt.bindLong(17, responsable_id);
        }
 
        Long cuentacontable_id = entity.getCuentacontable_id();
        if (cuentacontable_id != null) {
            stmt.bindLong(18, cuentacontable_id);
        }
 
        Long centrocosto_id = entity.getCentrocosto_id();
        if (centrocosto_id != null) {
            stmt.bindLong(19, centrocosto_id);
        }
 
        Long catalogo_id = entity.getCatalogo_id();
        if (catalogo_id != null) {
            stmt.bindLong(20, catalogo_id);
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
 
        String activopadre = entity.getActivopadre();
        if (activopadre != null) {
            stmt.bindString(3, activopadre);
        }
 
        String codigobarra = entity.getCodigobarra();
        if (codigobarra != null) {
            stmt.bindString(4, codigobarra);
        }
 
        String descripcion = entity.getDescripcion();
        if (descripcion != null) {
            stmt.bindString(5, descripcion);
        }
 
        String placa = entity.getPlaca();
        if (placa != null) {
            stmt.bindString(6, placa);
        }
 
        String marca = entity.getMarca();
        if (marca != null) {
            stmt.bindString(7, marca);
        }
 
        String modelo = entity.getModelo();
        if (modelo != null) {
            stmt.bindString(8, modelo);
        }
 
        String serie = entity.getSerie();
        if (serie != null) {
            stmt.bindString(9, serie);
        }
 
        String tipo = entity.getTipo();
        if (tipo != null) {
            stmt.bindString(10, tipo);
        }
 
        String expediente = entity.getExpediente();
        if (expediente != null) {
            stmt.bindString(11, expediente);
        }
 
        String ordencompra = entity.getOrdencompra();
        if (ordencompra != null) {
            stmt.bindString(12, ordencompra);
        }
 
        String factura = entity.getFactura();
        if (factura != null) {
            stmt.bindString(13, factura);
        }
 
        String fechacompra = entity.getFechacompra();
        if (fechacompra != null) {
            stmt.bindString(14, fechacompra);
        }
 
        String estado = entity.getEstado();
        if (estado != null) {
            stmt.bindString(15, estado);
        }
 
        Long ubicacion_id = entity.getUbicacion_id();
        if (ubicacion_id != null) {
            stmt.bindLong(16, ubicacion_id);
        }
 
        Long responsable_id = entity.getResponsable_id();
        if (responsable_id != null) {
            stmt.bindLong(17, responsable_id);
        }
 
        Long cuentacontable_id = entity.getCuentacontable_id();
        if (cuentacontable_id != null) {
            stmt.bindLong(18, cuentacontable_id);
        }
 
        Long centrocosto_id = entity.getCentrocosto_id();
        if (centrocosto_id != null) {
            stmt.bindLong(19, centrocosto_id);
        }
 
        Long catalogo_id = entity.getCatalogo_id();
        if (catalogo_id != null) {
            stmt.bindLong(20, catalogo_id);
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
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // activopadre
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // codigobarra
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // descripcion
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // placa
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // marca
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // modelo
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // serie
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // tipo
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // expediente
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // ordencompra
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // factura
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // fechacompra
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // estado
            cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15), // ubicacion_id
            cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16), // responsable_id
            cursor.isNull(offset + 17) ? null : cursor.getLong(offset + 17), // cuentacontable_id
            cursor.isNull(offset + 18) ? null : cursor.getLong(offset + 18), // centrocosto_id
            cursor.isNull(offset + 19) ? null : cursor.getLong(offset + 19) // catalogo_id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Activo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCodigo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setActivopadre(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCodigobarra(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDescripcion(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPlaca(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMarca(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setModelo(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSerie(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setTipo(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setExpediente(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setOrdencompra(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setFactura(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setFechacompra(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setEstado(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setUbicacion_id(cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15));
        entity.setResponsable_id(cursor.isNull(offset + 16) ? null : cursor.getLong(offset + 16));
        entity.setCuentacontable_id(cursor.isNull(offset + 17) ? null : cursor.getLong(offset + 17));
        entity.setCentrocosto_id(cursor.isNull(offset + 18) ? null : cursor.getLong(offset + 18));
        entity.setCatalogo_id(cursor.isNull(offset + 19) ? null : cursor.getLong(offset + 19));
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
    
    /** Internal query to resolve the "activoList" to-many relationship of Ubicacion. */
    public List<Activo> _queryUbicacion_ActivoList(Long ubicacion_id) {
        synchronized (this) {
            if (ubicacion_ActivoListQuery == null) {
                QueryBuilder<Activo> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Ubicacion_id.eq(null));
                ubicacion_ActivoListQuery = queryBuilder.build();
            }
        }
        Query<Activo> query = ubicacion_ActivoListQuery.forCurrentThread();
        query.setParameter(0, ubicacion_id);
        return query.list();
    }

    /** Internal query to resolve the "activoList" to-many relationship of Responsable. */
    public List<Activo> _queryResponsable_ActivoList(Long responsable_id) {
        synchronized (this) {
            if (responsable_ActivoListQuery == null) {
                QueryBuilder<Activo> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Responsable_id.eq(null));
                responsable_ActivoListQuery = queryBuilder.build();
            }
        }
        Query<Activo> query = responsable_ActivoListQuery.forCurrentThread();
        query.setParameter(0, responsable_id);
        return query.list();
    }

    /** Internal query to resolve the "activoList" to-many relationship of CuentaContable. */
    public List<Activo> _queryCuentaContable_ActivoList(Long cuentacontable_id) {
        synchronized (this) {
            if (cuentaContable_ActivoListQuery == null) {
                QueryBuilder<Activo> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Cuentacontable_id.eq(null));
                cuentaContable_ActivoListQuery = queryBuilder.build();
            }
        }
        Query<Activo> query = cuentaContable_ActivoListQuery.forCurrentThread();
        query.setParameter(0, cuentacontable_id);
        return query.list();
    }

    /** Internal query to resolve the "activoList" to-many relationship of CentroCosto. */
    public List<Activo> _queryCentroCosto_ActivoList(Long centrocosto_id) {
        synchronized (this) {
            if (centroCosto_ActivoListQuery == null) {
                QueryBuilder<Activo> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Centrocosto_id.eq(null));
                centroCosto_ActivoListQuery = queryBuilder.build();
            }
        }
        Query<Activo> query = centroCosto_ActivoListQuery.forCurrentThread();
        query.setParameter(0, centrocosto_id);
        return query.list();
    }

    /** Internal query to resolve the "activoList" to-many relationship of Catalogo. */
    public List<Activo> _queryCatalogo_ActivoList(Long catalogo_id) {
        synchronized (this) {
            if (catalogo_ActivoListQuery == null) {
                QueryBuilder<Activo> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Catalogo_id.eq(null));
                catalogo_ActivoListQuery = queryBuilder.build();
            }
        }
        Query<Activo> query = catalogo_ActivoListQuery.forCurrentThread();
        query.setParameter(0, catalogo_id);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getUbicacionDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getResponsableDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T2", daoSession.getCuentaContableDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T3", daoSession.getCentroCostoDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T4", daoSession.getCatalogoDao().getAllColumns());
            builder.append(" FROM ACTIVO T");
            builder.append(" LEFT JOIN UBICACION T0 ON T.\"UBICACION_ID\"=T0.\"_id\"");
            builder.append(" LEFT JOIN RESPONSABLE T1 ON T.\"RESPONSABLE_ID\"=T1.\"_id\"");
            builder.append(" LEFT JOIN CUENTA_CONTABLE T2 ON T.\"CUENTACONTABLE_ID\"=T2.\"_id\"");
            builder.append(" LEFT JOIN CENTRO_COSTO T3 ON T.\"CENTROCOSTO_ID\"=T3.\"_id\"");
            builder.append(" LEFT JOIN CATALOGO T4 ON T.\"CATALOGO_ID\"=T4.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Activo loadCurrentDeep(Cursor cursor, boolean lock) {
        Activo entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Ubicacion ubicacion = loadCurrentOther(daoSession.getUbicacionDao(), cursor, offset);
        entity.setUbicacion(ubicacion);
        offset += daoSession.getUbicacionDao().getAllColumns().length;

        Responsable responsable = loadCurrentOther(daoSession.getResponsableDao(), cursor, offset);
        entity.setResponsable(responsable);
        offset += daoSession.getResponsableDao().getAllColumns().length;

        CuentaContable cuentaContable = loadCurrentOther(daoSession.getCuentaContableDao(), cursor, offset);
        entity.setCuentaContable(cuentaContable);
        offset += daoSession.getCuentaContableDao().getAllColumns().length;

        CentroCosto centroCosto = loadCurrentOther(daoSession.getCentroCostoDao(), cursor, offset);
        entity.setCentroCosto(centroCosto);
        offset += daoSession.getCentroCostoDao().getAllColumns().length;

        Catalogo catalogo = loadCurrentOther(daoSession.getCatalogoDao(), cursor, offset);
        entity.setCatalogo(catalogo);

        return entity;    
    }

    public Activo loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Activo> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Activo> list = new ArrayList<Activo>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Activo> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Activo> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}