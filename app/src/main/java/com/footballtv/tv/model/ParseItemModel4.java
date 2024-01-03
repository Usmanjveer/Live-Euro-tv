package com.footballtv.tv.model;

public class ParseItemModel4 {
    private String title;
    private String imageLink;
    private String postLink;

    public ParseItemModel4(String title, String imageLink, String postLink) {
        this.title = title;
        this.imageLink = imageLink;
        this.postLink = postLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }
}
