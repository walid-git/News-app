package com.example.walid.project6;

public class Article {
    private String title;
    private String section;
    private String trailText;
    private String thumbnail;
    private String author;
    private String date;
    private String articleUrl;

    public Article(String title, String section, String trailText, String thumbnail, String author, String date,String articleUrl) {
        this.title = title;
        this.section = section;
        this.trailText = trailText;
        this.thumbnail = thumbnail;
        this.author = author;
        this.date = date;
        this.articleUrl = articleUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getTrailText() {
        return trailText;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getArticleUrl() {
        return articleUrl;
    }
}
