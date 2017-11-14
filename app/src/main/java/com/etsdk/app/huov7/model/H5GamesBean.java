package com.etsdk.app.huov7.model;

/**
 * Created by Administrator on 2017/11/3.
 */

public class H5GamesBean {
    private String gameName;
    private int imgId;
    private String weburl;
    private String introduce;
    private String type;

    public H5GamesBean(String gameName, int imgId, String weburl, String introduce, String type) {
        this.gameName = gameName;
        this.imgId = imgId;
        this.weburl = weburl;
        this.introduce = introduce;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGameName() {
        return gameName;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
