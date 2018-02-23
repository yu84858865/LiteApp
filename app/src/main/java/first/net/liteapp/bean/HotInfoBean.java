package first.net.liteapp.bean;

/**
 * Created by 10960 on 2018/2/23.
 */

public class HotInfoBean {

    private int id;
    private String title;
    private String imageUrl;
    private String lookCount;
    private String replyCount;
    private String praiseCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLookCount() {
        return lookCount;
    }

    public void setLookCount(String lookCount) {
        this.lookCount = lookCount;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
    }
}
