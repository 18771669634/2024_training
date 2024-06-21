package javabean;

public class Account {
    int id;
    String username;    // 登录的用户名
    String typename;   //类型
    int sImageId;   //被选中类型图片
    String beizhu;   //备注
    float money;  //价格
    String time ;  //保存时间字符串
    int year;
    int month;
    int day;
    int kind;   //类型  收入---1   支出---0

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public void setsImageId(int sImageId) {
        this.sImageId = sImageId;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public Account() {
    }


    public Account(int id, String username, String typename, int sImageId, String beizhu, float money, String time, int year, int month, int day, int kind) {
        this.id = id;
        this.username = username;
        this.typename = typename;
        this.sImageId = sImageId;
        this.beizhu = beizhu;
        this.money = money;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTypename() {
        return typename;
    }

    public int getsImageId() {
        return sImageId;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public float getMoney() {
        return money;
    }

    public String getTime() {
        return time;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getKind() {
        return kind;
    }
}
