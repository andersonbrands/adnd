package com.adnd.popularmovies.models;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieVideo {

    private String name;
    private String id;
    private String key;
    private String site;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static MovieVideo fromJSONString(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return fromJSONObject(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toJSONString() {
        return toJSONObject().toString();
    }

    public static MovieVideo fromJSONObject(JSONObject jsonObject) {
        MovieVideo movieVideo = new MovieVideo();

        try {
            movieVideo.name = jsonObject.getString("name");
            movieVideo.id = jsonObject.getString("id");
            movieVideo.key = jsonObject.getString("key");
            movieVideo.site = jsonObject.getString("site");
            movieVideo.type = jsonObject.getString("type");

        } catch (JSONException e) {
            e.printStackTrace();
            movieVideo = null;
        }

        return movieVideo;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", this.name);
            jsonObject.put("id", this.id);
            jsonObject.put("key", this.key);
            jsonObject.put("site", this.site);
            jsonObject.put("type", this.type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


}
