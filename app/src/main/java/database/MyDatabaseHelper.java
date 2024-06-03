package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "User.db";
    private static final int DB_VERSION = 1;
    private static MyDatabaseHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private static final String TABLE_NAME = "Post";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_BODY = "body";
    private MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 使用单例模式获取数据库帮助器的唯一实例
    public static MyDatabaseHelper getInstance(Context context) {
        if(mHelper == null) {
            mHelper = new MyDatabaseHelper(context);
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
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_BODY + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(Post post) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, post.userId);
        values.put(COLUMN_ID, post.id);
        values.put(COLUMN_TITLE, post.title);
        values.put(COLUMN_BODY, post.body);
        return mWDB.insert(TABLE_NAME, null, values);
    }

    public long deleteByUserId(String userId) {
        // 删除所有
        // mWDB.delete(TABLE_NAME, "1=1?", null);
        return mWDB.delete(TABLE_NAME, "userId=?", new String[] {userId});
    }

    public long update(Post post) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, post.userId);
        values.put(COLUMN_ID, post.id);
        values.put(COLUMN_TITLE, post.title);
        values.put(COLUMN_BODY, post.body);
        return mWDB.update(TABLE_NAME, values, "userId=?", new String[] {String.valueOf(post.userId)});
    }

    public List<Post> queryAll() {
        List<Post> list = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, null, null, null,null,null);
        // 循环取出游标指向的每条记录
        while(cursor.moveToNext()) {
            Post post = new Post();
            post.userId = cursor.getInt(0);
            post.id = cursor.getInt(1);
            post.title = cursor.getString(2);
            post.body = cursor.getString(3);
            list.add(post);
        }
        return list;
    }
}
