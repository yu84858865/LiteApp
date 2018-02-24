package first.net.liteapp.bean;

/**
 * Created by 10960 on 2018/2/23.
 */

public class HotInfoBean {

    private int id;
    private String title;
    private String imageUrl;
    private String skimCount;
    private String commentCount;
    private String praiseCount;

    public String getSkimCount() {
        return skimCount;
    }

    public void setSkimCount(String skimCount) {
        this.skimCount = skimCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

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


    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
    }
}
