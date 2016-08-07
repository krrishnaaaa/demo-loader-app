package com.pcsalt.example.demomphrx.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model call to hold value of webUrl and description, received from the JSON parsing and/or from
 * the database operations.
 */
public class WebUrl {
    /**
     * The {@link SerializedName} annotation is used for the key available in the JSON
     */
    @SerializedName("webUrl")
    private String webUrl;

    /**
     * The {@link SerializedName} annotation is used for the key available in the JSON
     */
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
