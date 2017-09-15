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

import com.evertvd.bienes.modelo.Activo;

import com.evertvd.bienes.modelo.Historial;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HISTORIAL".
*/
public class HistorialDao extends AbstractDao<Historial, Long> {

    public static final String TABLENAME = "HISTORIAL";

    /**
     * Properties of entity Historial.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Campo = new Property(1, String.class, "campo", false, "CAMPO");
        public final static Property Fechamodificacion = new Property(2, String.class, "fechamodificacion", false, "FECHAMODIFICACION");
        public final static Property Activo_id = new Property(3, Long.class, "activo_id", false, "ACTIVO_ID");
    }

    private DaoSession daoSession;

    private Query<Historial> activo_HistorialListQuery;

    public HistorialDao(DaoConfig config) {
        super(config);
    }
    
    public HistorialDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HISTORIAL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CAMPO\" TEXT," + // 1: campo
                "\"FECHAMODIFICACION\" TEXT," + // 2: fechamodificacion
                "\"ACTIVO_ID\" INTEGER);"); // 3: activo_id
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_HISTORIAL_ACTIVO_ID ON HISTORIAL" +
                " (\"ACTIVO_ID\");");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HISTORIAL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Historial entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String campo = entity.getCampo();
        if (campo != null) {
            stmt.bindString(2, campo);
        }
 
        String fechamodificacion = entity.getFechamodificacion();
        if (fechamodificacion != null) {
            stmt.bindString(3, fechamodificacion);
        }
 
        Long activo_id = entity.getActivo_id();
        if (activo_id != null) {
            stmt.bindLong(4, activo_id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Historial entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String campo = entity.getCampo();
        if (campo != null) {
            stmt.bindString(2, campo);
        }
 
        String fechamodificacion = entity.getFechamodificacion();
        if (fechamodificacion != null) {
            stmt.bindString(3, fechamodificacion);
        }
 
        Long activo_id = entity.getActivo_id();
        if (activo_id != null) {
            stmt.bindLong(4, activo_id);
        }
    }

    @Override
    protected final void attachEntity(Historial entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Historial readEntity(Cursor cursor, int offset) {
        Historial entity = new Historial( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // campo
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // fechamodificacion
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // activo_id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Historial entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCampo(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFechamodificacion(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setActivo_id(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Historial entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Historial entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Historial entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "historialList" to-many relationship of Activo. */
    public List<Historial> _queryActivo_HistorialList(Long activo_id) {
        synchronized (this) {
            if (activo_HistorialListQuery == null) {
                QueryBuilder<Historial> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Activo_id.eq(null));
                activo_HistorialListQuery = queryBuilder.build();
            }
        }
        Query<Historial> query = activo_HistorialListQuery.forCurrentThread();
        query.setParameter(0, activo_id);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getActivoDao().getAllColumns());
            builder.append(" FROM HISTORIAL T");
            builder.append(" LEFT JOIN ACTIVO T0 ON T.\"ACTIVO_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Historial loadCurrentDeep(Cursor cursor, boolean lock) {
        Historial entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Activo activo = loadCurrentOther(daoSession.getActivoDao(), cursor, offset);
        entity.setActivo(activo);

        return entity;    
    }

    public Historial loadDeep(Long key) {
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
    public List<Historial> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Historial> list = new ArrayList<Historial>(count);
        
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
    
    protected List<Historial> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Historial> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}