package com.example.duan1_quanlysalon.model;

import java.util.ArrayList;

public class Channel {
    String title;
    String description;
    Image image;
    String pubDate;
    String generator;
    String link;
    ArrayList<Item> item;

    public Channel(String title, String description, Image image, String pubDate, String generator, String link, ArrayList<Item> item) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.pubDate = pubDate;
        this.generator = generator;
        this.link = link;
        this.item = item;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGenerator() {
        return generator;
    }

    public void setgenerator(String generatior) {
        this.generator = generatior;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }
}
