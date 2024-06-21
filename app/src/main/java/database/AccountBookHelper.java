package database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javabean.Account;
import javabean.Type;

public class AccountBookHelper{
    private static SQLiteDatabase db;
    private static final String TAG = "AccountBookHelper";

    /* 初始化数据库对象*/
    public static void initDB(Context context){
        DBOpenHelper helper = new DBOpenHelper(context);  //得到帮助类对象
        db = helper.getWritableDatabase();      //得到数据库对象
    }


    /**
     * 读取数据库当中的数据，写入内存集合里
     *   kind :表示收入或者支出
     * */
    public static List<Type> getTypeList(int kind){
        List<Type>list = new ArrayList<>();
        //读取typetb表当中的数据
        Cursor cursor = db.query("typetb", null, "kind = ?", new String[]{String.valueOf(kind)}, null, null, null);

        // 循环读取游标内容，存储到对象当中
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            Type type = new Type(id, typename, imageId, sImageId, kind1);
            list.add(type);
        }
        cursor.close();
        return list;
    }


    // 插入一条账本记录
    public static long insertRecord(Account account) {
        ContentValues values = new ContentValues();
        values.put("username",account.getUsername());
        values.put("typename",account.getTypename());
        values.put("sImageId",account.getsImageId());
        values.put("beizhu",account.getBeizhu());
        values.put("money",account.getMoney());
        values.put("time",account.getTime());
        values.put("year",account.getYear());
        values.put("month",account.getMonth());
        values.put("day",account.getDay());
        values.put("kind",account.getKind());

        long result = db.insert("userAccount", null, values);
        Log.d(TAG, "Inserted new record into " + "userAccount" + " with result: " + result + " ok!!!");
        return result;
    }


    /*
     * 获取记账表当中 某一天 的所有支出或者收入情况
     * 实现按用户名筛选
     * */
    public static List<Account> getAccountListOneDayFromAccountbook(String usname, int year, int month, int day){
        List<Account>list = new ArrayList<>();
        Cursor cursor = db.query(
                "userAccount",                       // 表名
                null,                              // 列名数组，null表示查询所有列
                "username = ? AND year = ? AND month = ? AND day = ?", // WHERE 子句
                new String[]{usname, String.valueOf(year), String.valueOf(month), String.valueOf(day)}, // WHERE 子句的参数值
                null,                              // GROUP BY 子句
                null,                              // HAVING 子句
                "id DESC"                          // ORDER BY 子句
        );

        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            // @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            @SuppressLint("Range") String beizhu = cursor.getString(cursor.getColumnIndexOrThrow("beizhu"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));

            Account account = new Account(id, usname, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(account);
        }
        cursor.close();
        return list;
    }


    /*
     * 获取记账表当中的 所有 支出或者收入 情况
     * 实现按用户名筛选
     * 按添加的顺序（id）降序 排列
     * */
    public static List<Account> getAccountListAllFromAccountbook(String usname){
        List<Account>list = new ArrayList<>();
        Cursor cursor = db.query(
                "userAccount",                       // 表名
                null,                              // 列名数组，null表示查询所有列
                "username = ?", // WHERE 子句
                new String[]{usname}, // WHERE 子句的参数值
                null,                              // GROUP BY 子句
                null,                              // HAVING 子句
                "id DESC"                          // ORDER BY 子句
        );

        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            // @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            String beizhu = cursor.getString(cursor.getColumnIndexOrThrow("beizhu"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));;
            int month = cursor.getInt(cursor.getColumnIndexOrThrow("month"));;
            int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));;

            Account account = new Account(id, usname, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(account);
        }
        cursor.close();
        return list;
    }


    /*
     * 获取记账表当中的 所有 支出或者收入 情况
     * 实现按用户名筛选
     * 按 时间（time）降序 排列
     * */
    public static List<Account> getAccountListAllFromAccountbookByTime(String usname){
        List<Account>list = new ArrayList<>();
        Cursor cursor = db.query(
                "userAccount",                       // 表名
                null,                              // 列名数组，null表示查询所有列
                "username = ?", // WHERE 子句
                new String[]{usname}, // WHERE 子句的参数值
                null,                              // GROUP BY 子句
                null,                              // HAVING 子句
                "time DESC"                          // ORDER BY 子句
        );

        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            // @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            String beizhu = cursor.getString(cursor.getColumnIndexOrThrow("beizhu"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));;
            int month = cursor.getInt(cursor.getColumnIndexOrThrow("month"));;
            int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));;

            Account account = new Account(id, usname, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(account);
        }
        cursor.close();
        return list;
    }


    /*
     * 获取记账表当中的 所有 支出或者收入 情况
     * 实现按用户名筛选
     * 按 金钱（money）降序 排列
     * */
    public static List<Account> getAccountListAllFromAccountbookByMoney(String usname){
        List<Account>list = new ArrayList<>();
        Cursor cursor = db.query(
                "userAccount",                       // 表名
                null,                              // 列名数组，null表示查询所有列
                "username = ?", // WHERE 子句
                new String[]{usname}, // WHERE 子句的参数值
                null,                              // GROUP BY 子句
                null,                              // HAVING 子句
                "money DESC"                          // ORDER BY 子句
        );

        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            // @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String typename = cursor.getString(cursor.getColumnIndexOrThrow("typename"));
            int sImageId = cursor.getInt(cursor.getColumnIndexOrThrow("sImageId"));
            String beizhu = cursor.getString(cursor.getColumnIndexOrThrow("beizhu"));
            float money = cursor.getFloat(cursor.getColumnIndexOrThrow("money"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            int kind = cursor.getInt(cursor.getColumnIndexOrThrow("kind"));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow("year"));;
            int month = cursor.getInt(cursor.getColumnIndexOrThrow("month"));;
            int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));;

            Account account = new Account(id, usname, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(account);
        }
        cursor.close();
        return list;
    }


    /*
     * 获取记账表当中 某一天 的 所有 支出 或 收入 总金额； kind : 0(支出)  1(收入)
     * 实现按用户名筛选
     * */
    public static float getSumMoneyOneDay_Out_In(String usname, int year, int month, int day, int kind) {
        float total = 0;

        Cursor cursor = db.query(
                "userAccount",                 // 表名
                new String[]{"SUM(money)"},         // 列名数组，聚合查询. 这里我们指定要查询的列名是 SUM(money)，表示我们希望查询的结果包含 money 列的总和
                "username = ? AND year = ? AND month = ? AND day = ? AND kind = ?", // WHERE 子句
                new String[]{usname, String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(kind)}, // WHERE 子句的参数值
                null,                              // GROUP BY 子句
                null,                              // HAVING 子句
                null                          // ORDER BY 子句 排列
        );

        // 遍历结果
        // 对于聚合查询（例如 SUM），结果集通常会包含一行数据，moveToFirst() 方法用于将 Cursor 移动到结果集的第一行
        if (cursor.moveToFirst()) {
            // 使用 getColumnIndexOrThrow 来获取列索引
            int columnIndex = cursor.getColumnIndexOrThrow("SUM(money)");
            // 返回指定索引位置处列的浮点数值
            total = cursor.getFloat(columnIndex);
        }

        // 关闭游标
        cursor.close();

        return total;
    }


    /*
     * 获取记账表当中的 所有 支出 或者 收入 总金额； kind : 0(支出)  1(收入)
     * 实现按用户名筛选
     * */
    public static float getSumMoneyTotal_Out_In(String usname, int kind) {
        float total = 0;

        Cursor cursor = db.query(
                "userAccount",                 // 表名
                new String[]{"SUM(money)"},         // 列名数组，聚合查询. 这里我们指定要查询的列名是 SUM(money)，表示我们希望查询的结果包含 money 列的总和
                "username = ? AND kind = ?", // WHERE 子句
                new String[]{usname, String.valueOf(kind)}, // WHERE 子句的参数值
                null,                              // GROUP BY 子句
                null,                              // HAVING 子句
                null                          // ORDER BY 子句 排列
        );

        // 遍历结果
        // 对于聚合查询（例如 SUM），结果集通常会包含一行数据，moveToFirst() 方法用于将 Cursor 移动到结果集的第一行
        if (cursor.moveToFirst()) {
            // 使用 getColumnIndexOrThrow 来获取列索引
            int columnIndex = cursor.getColumnIndexOrThrow("SUM(money)");
            // 返回指定索引位置处列的浮点数值
            total = cursor.getFloat(columnIndex);
        }

        // 关闭游标
        cursor.close();

        return total;
    }


}
