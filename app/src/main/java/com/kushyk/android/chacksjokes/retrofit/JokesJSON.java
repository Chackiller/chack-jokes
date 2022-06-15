package com.kushyk.android.chacksjokes.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JokesJSON {
    @SerializedName("icon_url")
    @Expose
    String icon_url;

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("value")
    @Expose
    String value;

    public JokesJSON(String icon_url, String id, String url, String value) {
        this.icon_url = icon_url;
        this.id = id;
        this.url = url;
        this.value = value;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
