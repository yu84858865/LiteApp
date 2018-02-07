package first.net.liteapp.bean;

/**
 * Created by yuqiubo on 2018/2/2.
 */

public class TabLiveBean {
    private String name;
    private String info;
    private int count;
    private String img;

    public String getName() {
        return name;
    }

    public TabLiveBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public TabLiveBean setInfo(String info) {
        this.info = info;
        return this;
    }

    public int getCount() {
        return count;
    }

    public TabLiveBean setCount(int count) {
        this.count = count;
        return this;
    }

    public String getImg() {
        return img;
    }

    public TabLiveBean setImg(String img) {
        this.img = img;
        return this;
    }
}
