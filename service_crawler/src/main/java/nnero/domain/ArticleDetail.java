package nnero.domain;

/**
 * 2017/8/3 下午5:52 created by NNERO
 */
public class ArticleDetail {

    private int adId;

    private String originId;

    private String content;

    public ArticleDetail(){}

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
