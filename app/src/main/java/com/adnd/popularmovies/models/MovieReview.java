package com.adnd.popularmovies.models;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieReview {

    private String author;
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static MovieReview fromJSONString(String jsonString) {
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

    public static MovieReview fromJSONObject(JSONObject jsonObject) {
        MovieReview movieVideo = new MovieReview();

        try {
            movieVideo.author = jsonObject.getString("author");
            movieVideo.content = jsonObject.getString("content");

        } catch (JSONException e) {
            e.printStackTrace();
            movieVideo = null;
        }

        return movieVideo;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("author", this.author);
            jsonObject.put("content", this.content);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


}
