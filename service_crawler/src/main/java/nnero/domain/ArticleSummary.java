package nnero.domain;

import java.sql.Timestamp;

/**
 * 2017/8/3 上午11:06 created by NNERO
 */
public class ArticleSummary {

    private int asId;

    private String originId;

    private String title;

    private String summary;

    private String author;

    private Timestamp writeTime;

    private Timestamp lastModifyTime;

    public ArticleSummary(){}

    public int getAsId() {
        return asId;
    }

    public void setAsId(int asId) {
        this.asId = asId;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Timestamp writeTime) {
        this.writeTime = writeTime;
    }

    public Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Timestamp lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Override
    public String toString() {
        return "ArticleSummary{" +
                "asId=" + asId +
                ", originId='" + originId + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", author='" + author + '\'' +
                ", writeTime=" + writeTime +
                ", lastModifyTime=" + lastModifyTime +
                '}';
    }
}
