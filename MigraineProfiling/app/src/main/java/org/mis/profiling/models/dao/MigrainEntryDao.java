package org.mis.profiling.models.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import org.mis.profiling.models.MigrainEntry;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MIGRAIN_ENTRY".
*/
public class MigrainEntryDao extends AbstractDao<MigrainEntry, Long> {

    public static final String TABLENAME = "MIGRAIN_ENTRY";

    /**
     * Properties of entity MigrainEntry.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Started = new Property(1, java.util.Date.class, "started", false, "STARTED");
        public final static Property Ended = new Property(2, java.util.Date.class, "ended", false, "ENDED");
        public final static Property Level = new Property(3, int.class, "level", false, "LEVEL");
        public final static Property Timeofday = new Property(4, String.class, "timeofday", false, "TIMEOFDAY");
        public final static Property Cause = new Property(5, String.class, "cause", false, "CAUSE");
        public final static Property Remedy = new Property(6, String.class, "remedy", false, "REMEDY");
        public final static Property Medicine = new Property(7, String.class, "medicine", false, "MEDICINE");
    };


    public MigrainEntryDao(DaoConfig config) {
        super(config);
    }
    
    public MigrainEntryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MIGRAIN_ENTRY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"STARTED\" INTEGER NOT NULL ," + // 1: started
                "\"ENDED\" INTEGER NOT NULL ," + // 2: ended
                "\"LEVEL\" INTEGER NOT NULL ," + // 3: level
                "\"TIMEOFDAY\" TEXT," + // 4: timeofday
                "\"CAUSE\" TEXT," + // 5: cause
                "\"REMEDY\" TEXT," + // 6: remedy
                "\"MEDICINE\" TEXT);"); // 7: medicine
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MIGRAIN_ENTRY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, MigrainEntry entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getStarted().getTime());
        stmt.bindLong(3, entity.getEnded().getTime());
        stmt.bindLong(4, entity.getLevel());
 
        String timeofday = entity.getTimeofday();
        if (timeofday != null) {
            stmt.bindString(5, timeofday);
        }
 
        String cause = entity.getCause();
        if (cause != null) {
            stmt.bindString(6, cause);
        }
 
        String remedy = entity.getRemedy();
        if (remedy != null) {
            stmt.bindString(7, remedy);
        }
 
        String medicine = entity.getMedicine();
        if (medicine != null) {
            stmt.bindString(8, medicine);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public MigrainEntry readEntity(Cursor cursor, int offset) {
        MigrainEntry entity = new MigrainEntry( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            new java.util.Date(cursor.getLong(offset + 1)), // started
            new java.util.Date(cursor.getLong(offset + 2)), // ended
            cursor.getInt(offset + 3), // level
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // timeofday
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // cause
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // remedy
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // medicine
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, MigrainEntry entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStarted(new java.util.Date(cursor.getLong(offset + 1)));
        entity.setEnded(new java.util.Date(cursor.getLong(offset + 2)));
        entity.setLevel(cursor.getInt(offset + 3));
        entity.setTimeofday(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCause(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRemedy(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMedicine(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(MigrainEntry entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(MigrainEntry entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
