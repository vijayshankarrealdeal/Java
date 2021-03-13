package com.example.myapplication;

import java.lang.reflect.Array;

public class AdminModel {
    private String itemId;
    private String cloudStorageLink;
    private String discription;
    private String name;
    private String socialHandle;
    private String source;
    private String sourceLink;
    private String title;
    private String webpage;
    private String views;

    private AdminModel() {
    }

    private AdminModel(String cloudStorageLink, String discription, String name, String socialHandle, String source, String sourceLink,
                       String title, String webpage, String views) {
        this.cloudStorageLink = cloudStorageLink;
        this.discription = discription;
        this.name = name;
        this.socialHandle = socialHandle;
        this.source = source;
        this.sourceLink = sourceLink;
        this.title = title;
        this.webpage = webpage;
        this.views = views;
    }

    public String getName() {
        return name;
    }

    public String getCloudStorageLink() {
        return cloudStorageLink;
    }

    public String getDiscription() {
        return discription;
    }

    public String getSocialHandle() {
        return socialHandle;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getWebpage() {
        return webpage;
    }

    public String views() {
        return views;
    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
