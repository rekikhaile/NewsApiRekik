package com.rekik.newsapirekik.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopHeadline
{
    private String status;

    private int totalResults;
    private ArrayList<Article> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "TopHeadline{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }

    /*public String getStatus() { return this.status; }

    public void setStatus(String status) { this.status = status; }

    public int getTotalResults() { return this.totalResults; }

    public void setTotalResults(int totalResults) { this.totalResults = totalResults; }

    public ArrayList<Article> getArticles() { return this.articles; }

    public void setArticles(ArrayList<Article> articles) { this.articles = articles; }
*/
}
