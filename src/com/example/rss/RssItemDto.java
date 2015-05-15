package com.example.rss;

import com.example.rss.util.FeedUtil;

import java.util.Date;

public class RssItemDto {

    private String title;
    private String description;
    private Date pubDate;
    private String link;

    private String content;

    public RssItemDto(String title, String description, Date pubDate, String link, String content) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.content = content;
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

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return FeedUtil.formatRssItem(this);
    }
}