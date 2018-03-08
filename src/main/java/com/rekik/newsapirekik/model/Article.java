package com.rekik.newsapirekik.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article
{
    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private Date publishedAt;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "Article{" +
                "source=" + source +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", publishedAt=" + publishedAt +
                '}';
    }

    /*public Source getSource() { return this.source; }

    public void setSource(Source source) { this.source = source; }



    public String getAuthor() { return this.author; }

    public void setAuthor(String author) { this.author = author; }



    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }



    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }



    public String getUrl() { return this.url; }

    public void setUrl(String url) { this.url = url; }



    public String getUrlToImage() { return this.urlToImage; }

    public void setUrlToImage(String urlToImage) { this.urlToImage = urlToImage; }



    public Date getPublishedAt() { return this.publishedAt; }

    public void setPublishedAt(Date publishedAt) { this.publishedAt = publishedAt; }
*/
}