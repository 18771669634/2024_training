package javabean;

/*
* 收入或者支出具体类型的类
* */
public class Type {
    int id;
    String typename;
    int imageId;    // 未被选中的图片id
    int simageId;   // 被选中的图片id
    int kind;   // 支出 0， 收入 1

    public Type() {
    }

    public Type(int id, String typename, int imageId, int simageId, int kind) {
        this.id = id;
        this.typename = typename;
        this.imageId = imageId;
        this.simageId = simageId;
        this.kind = kind;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setSimageId(int simageId) {
        this.simageId = simageId;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public String getTypename() {
        return typename;
    }

    public int getImageId() {
        return imageId;
    }

    public int getSimageId() {
        return simageId;
    }

    public int getKind() {
        return kind;
    }
}
