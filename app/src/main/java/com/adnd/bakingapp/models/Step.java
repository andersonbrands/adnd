package com.adnd.bakingapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        tableName = "steps",
        foreignKeys = @ForeignKey(
                entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipe_id",
                onDelete = CASCADE,
                onUpdate = CASCADE
        ),
        primaryKeys = {"id", "recipe_id"},
        indices = {@Index("recipe_id")}
)
public class Step {

    private int recipe_id;

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }


    public static Step fromJSONString(String jsonString) {
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

    public static Step fromJSONObject(JSONObject jsonObject) {
        Step step = new Step();

        try {
            step.id = Integer.parseInt(jsonObject.getString("id"));
            step.shortDescription = jsonObject.getString("shortDescription");
            step.description = jsonObject.getString("description");
            step.videoURL = jsonObject.getString("videoURL");
            step.thumbnailURL = jsonObject.getString("thumbnailURL");
        } catch (JSONException | NumberFormatException e) {
            e.printStackTrace();
            step = null;
        }

        return step;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", this.id);
            jsonObject.put("shortDescription", this.shortDescription);
            jsonObject.put("description", this.description);
            jsonObject.put("videoURL", this.videoURL);
            jsonObject.put("thumbnailURL", this.thumbnailURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
