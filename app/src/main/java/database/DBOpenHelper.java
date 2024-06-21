package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.accountbook.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "AccountDB.db";
    private static final int DB_VERSION = 1;
    private final static String type_tb = "typetb";
    private final static String userTable = "userAccount";

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTypeQuery = "CREATE TABLE IF NOT EXISTS " + type_tb + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "typename TEXT, " +
                "imageId INTEGER, " +
                "sImageId INTEGER, " +
                "kind INTEGER)";
        // 创建表示类型的表
        db.execSQL(createTypeQuery);
        insertType(db);

        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + userTable + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "typename TEXT, " +
                "sImageId INTEGER, " +
                "beizhu TEXT, " +
                "money FLOAT, " +
                "time TEXT, " +
                "year INTEGER, " +
                "month INTEGER, " +
                "day INTEGER, " +
                "kind INTEGER)";
        // 创建记账表
        db.execSQL(createTableQuery);
    }

    private void insertType(SQLiteDatabase db) {
        // 向typetb表当中插入元素
        String sql = "insert into typetb (typename, imageId, sImageId, kind) values (?,?,?,?)";

        db.execSQL(sql, new Object[]{"餐饮", R.mipmap.food,R.mipmap.food_fs, 0});
        db.execSQL(sql, new Object[]{"日用", R.mipmap.daily,R.mipmap.daily_fs, 0});
        db.execSQL(sql, new Object[]{"出行", R.mipmap.travel,R.mipmap.travel_fs, 0});
        db.execSQL(sql, new Object[]{"娱乐", R.mipmap.play,R.mipmap.play_fs, 0});
        db.execSQL(sql, new Object[]{"其他", R.mipmap.other,R.mipmap.other_fs, 0});

        db.execSQL(sql, new Object[]{"薪资", R.mipmap.in_wage,R.mipmap.in_wage_fs, 1});
        db.execSQL(sql, new Object[]{"奖金", R.mipmap.in_zhongjiang,R.mipmap.in_zhongjiang_fs, 1});
        db.execSQL(sql, new Object[]{"转账", R.mipmap.in_transfer,R.mipmap.in_transfer_fs, 1});
        db.execSQL(sql, new Object[]{"生活费", R.mipmap.in_living,R.mipmap.in_living_fs, 1});
        db.execSQL(sql, new Object[]{"其他", R.mipmap.other,R.mipmap.in_other_fs, 1});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
