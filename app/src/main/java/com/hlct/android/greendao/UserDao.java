package com.hlct.android.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hlct.android.bean.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserCode = new Property(1, String.class, "userCode", false, "USER_CODE");
        public final static Property LoginName = new Property(2, String.class, "loginName", false, "LOGIN_NAME");
        public final static Property Password = new Property(3, String.class, "password", false, "PASSWORD");
        public final static Property Email = new Property(4, String.class, "email", false, "EMAIL");
        public final static Property Sex = new Property(5, String.class, "sex", false, "SEX");
        public final static Property Telephone = new Property(6, String.class, "telephone", false, "TELEPHONE");
        public final static Property Name = new Property(7, String.class, "name", false, "NAME");
        public final static Property DepartmentId = new Property(8, Long.class, "departmentId", false, "DEPARTMENT_ID");
        public final static Property DepartmentName = new Property(9, String.class, "departmentName", false, "DEPARTMENT_NAME");
        public final static Property BankName = new Property(10, String.class, "bankName", false, "BANK_NAME");
        public final static Property BankId = new Property(11, Long.class, "bankId", false, "BANK_ID");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_CODE\" TEXT," + // 1: userCode
                "\"LOGIN_NAME\" TEXT," + // 2: loginName
                "\"PASSWORD\" TEXT," + // 3: password
                "\"EMAIL\" TEXT," + // 4: email
                "\"SEX\" TEXT," + // 5: sex
                "\"TELEPHONE\" TEXT," + // 6: telephone
                "\"NAME\" TEXT," + // 7: name
                "\"DEPARTMENT_ID\" INTEGER," + // 8: departmentId
                "\"DEPARTMENT_NAME\" TEXT," + // 9: departmentName
                "\"BANK_NAME\" TEXT," + // 10: bankName
                "\"BANK_ID\" INTEGER);"); // 11: bankId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userCode = entity.getUserCode();
        if (userCode != null) {
            stmt.bindString(2, userCode);
        }
 
        String loginName = entity.getLoginName();
        if (loginName != null) {
            stmt.bindString(3, loginName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(5, email);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(6, sex);
        }
 
        String telephone = entity.getTelephone();
        if (telephone != null) {
            stmt.bindString(7, telephone);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        Long departmentId = entity.getDepartmentId();
        if (departmentId != null) {
            stmt.bindLong(9, departmentId);
        }
 
        String departmentName = entity.getDepartmentName();
        if (departmentName != null) {
            stmt.bindString(10, departmentName);
        }
 
        String bankName = entity.getBankName();
        if (bankName != null) {
            stmt.bindString(11, bankName);
        }
 
        Long bankId = entity.getBankId();
        if (bankId != null) {
            stmt.bindLong(12, bankId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userCode = entity.getUserCode();
        if (userCode != null) {
            stmt.bindString(2, userCode);
        }
 
        String loginName = entity.getLoginName();
        if (loginName != null) {
            stmt.bindString(3, loginName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(4, password);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(5, email);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(6, sex);
        }
 
        String telephone = entity.getTelephone();
        if (telephone != null) {
            stmt.bindString(7, telephone);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        Long departmentId = entity.getDepartmentId();
        if (departmentId != null) {
            stmt.bindLong(9, departmentId);
        }
 
        String departmentName = entity.getDepartmentName();
        if (departmentName != null) {
            stmt.bindString(10, departmentName);
        }
 
        String bankName = entity.getBankName();
        if (bankName != null) {
            stmt.bindString(11, bankName);
        }
 
        Long bankId = entity.getBankId();
        if (bankId != null) {
            stmt.bindLong(12, bankId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // loginName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // password
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // email
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // sex
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // telephone
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // name
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8), // departmentId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // departmentName
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // bankName
            cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11) // bankId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLoginName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPassword(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setEmail(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSex(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTelephone(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDepartmentId(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
        entity.setDepartmentName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setBankName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setBankId(cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(User entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(User entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
