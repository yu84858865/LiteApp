package first.net.liteapp.bean;

import java.io.Serializable;

/**
 * Created by 10960 on 2018/2/22.
 */

public class CycleViewBean implements Serializable{


    /**
     * rows : 10
     * page : 1
     * sortOrder : null
     * id : 76
     * photoUrl : http://testimg.ibaking.com.cn/ad/699a8b70c98741a8b00b59d92f03f4ed.jpg
     * link : www.baidu.com
     * sheme : 1
     * timeCount : 0
     * taredate : 2017-12-26 00:00:52
     * deadline : 2018-03-31 23:55:55
     * clickCount : 364
     * count : 0
     * openCount : 0
     * showDuration : 2
     * title : 测试年终统计
     * linkType : 3
     * adType : 2
     * resType : 1
     * videoUrl : null
     */

    private int rows;
    private int page;
    private Object sortOrder;
    private int id;
    private String photoUrl;
    private String link;
    private int sheme;
    private int timeCount;
    private String taredate;
    private String deadline;
    private int clickCount;
    private int count;
    private int openCount;
    private int showDuration;
    private String title;
    private int linkType;
    private int adType;
    private int resType;
    private Object videoUrl;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Object getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Object sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSheme() {
        return sheme;
    }

    public void setSheme(int sheme) {
        this.sheme = sheme;
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }

    public String getTaredate() {
        return taredate;
    }

    public void setTaredate(String taredate) {
        this.taredate = taredate;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    public int getShowDuration() {
        return showDuration;
    }

    public void setShowDuration(int showDuration) {
        this.showDuration = showDuration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public int getResType() {
        return resType;
    }

    public void setResType(int resType) {
        this.resType = resType;
    }

    public Object getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(Object videoUrl) {
        this.videoUrl = videoUrl;
    }
}
