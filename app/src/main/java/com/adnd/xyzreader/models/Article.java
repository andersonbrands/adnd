package com.adnd.xyzreader.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "articles")
public class Article {

    @PrimaryKey
    private int id;

    private String title;
    private String author;
    private String body;
    private String thumb;
    private String photo;
    private String published_date;
    private float aspect_ratio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public float getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(float aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public static Article fromJSONString(String jsonString) {
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

    public static Article fromJSONObject(JSONObject jsonObject) {
        Article article = new Article();

        try {
            article.id = Integer.parseInt(jsonObject.getString("id"));
            article.title = jsonObject.getString("title");
            article.author = jsonObject.getString("author");
            article.body = jsonObject.getString("body");
            article.thumb = jsonObject.getString("thumb");
            article.photo = jsonObject.getString("photo");
            article.published_date = jsonObject.getString("published_date");
            article.aspect_ratio = Float.parseFloat(jsonObject.getString("aspect_ratio"));
        } catch (JSONException e) {
            e.printStackTrace();
            article = null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            article = null;
        }

        return article;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("title", this.title);
            jsonObject.put("author", this.author);
            jsonObject.put("body", this.body);
            jsonObject.put("thumb", this.thumb);
            jsonObject.put("photo", this.photo);
            jsonObject.put("published_date", this.published_date);
            jsonObject.put("aspect_ratio", this.aspect_ratio);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
