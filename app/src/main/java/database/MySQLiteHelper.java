package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.accountbook.R;

import java.util.ArrayList;
import java.util.List;

import javabean.User;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MySqlite.db";
    private static final int DB_VERSION = 1;
    private static MySQLiteHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";




    private String creat_user = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            COLUMN_NAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT)";

    // 获取数据库名称
    public static String getDbName() {
        return DB_NAME;
    }

    // 获取数据库版本
    public static int getDbVersion() {
        return DB_VERSION;
    }

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 使用线程安全的单例模式（懒汉模式）获取数据库帮助器的唯一实例
    public static synchronized MySQLiteHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new MySQLiteHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if(mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if(mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if(mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }

        if(mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creat_user);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 注册
    public long register(User u) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, u.name);
        values.put(COLUMN_PASSWORD, u.password);
        return mWDB.insert(TABLE_NAME, null, values);
    }

    // 登录
    public boolean login(String name, String password) {
        boolean result = false;
        Cursor cursor_users = mWDB.query(TABLE_NAME,null,"name = ?", new String[]{name},null,null,null);
        if(cursor_users != null) {
            while(cursor_users.moveToNext()) {
                String password_db = cursor_users.getString(1);
                result = password_db.equals(password);
                return result;
            }
            cursor_users.close();
        }
        return result;
    }

    // 检查用户名是否存在
    public boolean isUsernameExists(String username) {
        boolean exists = false;
        String[] columns = {COLUMN_NAME};
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = mRDB.query(
                TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);

        if (cursor != null) {
            exists = cursor.getCount() > 0;
            cursor.close();
        }
        return exists;
    }
}
