package com.pcsalt.example.demomphrx.model;

import com.google.gson.annotations.SerializedName;

public class WebUrl {
    @SerializedName("webUrl")
    private String webUrl;

    @SerializedName("description")
    private String description;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
