package com.corelambda.tourist.datamodel;

import com.google.gson.annotations.SerializedName;

public class WikipediaPage {
    @SerializedName("pageid")
    private long pageid;

    @SerializedName("title")
    private String title;


    @SerializedName("index")
    private int index;

    @SerializedName("thumbnail")
    private WikipediaImage thumbnail;

    @SerializedName("pageImage")
    private String pageImage;

    public long getPageid() {
        return pageid;
    }

    public void setPageid(long pageid) {
        this.pageid = pageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public WikipediaImage getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(WikipediaImage thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPageImage() {
        return pageImage;
    }

    public void setPageImage(String pageImage) {
        this.pageImage = pageImage;
    }
}
