package np.com.onlineclothingshop.models;

public class Item {
    private int id;
    private String name;
    private int price;
    private String imageName;
    private String desc;

    public Item(int id, String name, int price, String imageName, String desc) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageName = imageName;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}