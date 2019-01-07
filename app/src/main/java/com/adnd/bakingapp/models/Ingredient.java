package com.adnd.bakingapp.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(
        tableName = "ingredients",
        foreignKeys = @ForeignKey(
                entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipe_id"
        ),
        indices = {@Index("recipe_id")}
)
public class Ingredient {

    private int recipe_id;

    @PrimaryKey(autoGenerate = true)
    private int id;
    private float quantity;
    private String measure;
    private String ingredient;


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

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public static Ingredient fromJSONString(String jsonString) {
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

    public static Ingredient fromJSONObject(JSONObject jsonObject) {
        Ingredient ingredient = new Ingredient();

        try {
            ingredient.quantity = Float.parseFloat(jsonObject.getString("quantity"));
            ingredient.measure = jsonObject.getString("measure");
            ingredient.ingredient = jsonObject.getString("ingredient");
        } catch (JSONException | NumberFormatException e) {
            e.printStackTrace();
            ingredient = null;
        }

        return ingredient;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("quantity", this.quantity);
            jsonObject.put("measure", this.measure);
            jsonObject.put("ingredient", this.ingredient);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
