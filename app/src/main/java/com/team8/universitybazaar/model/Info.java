package com.team8.universitybazaar.model;

public class Info {

    int infoId;
    String author;
    String title;
    String content;
    String date;

    public Info() {

    }

    public Info(int infoId, String author, String title, String content, String date) {

        this.infoId = infoId;
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getPostId() {
        return infoId;
    }

    public void setPostId(int postId) {
        this.infoId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
