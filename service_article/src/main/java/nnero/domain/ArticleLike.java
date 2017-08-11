package nnero.domain;

import java.sql.Timestamp;

/**
 * 2017/8/6 下午4:04 created by NNERO
 */
public class ArticleLike {

    private int alId;

    private String originId;

    private String uId;

    private Timestamp likeTime;

    public ArticleLike(){}

    public int getAlId() {
        return alId;
    }

    public void setAlId(int alId) {
        this.alId = alId;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Timestamp getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Timestamp likeTime) {
        this.likeTime = likeTime;
    }
}
