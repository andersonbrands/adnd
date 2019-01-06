package com.adnd.bakingapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient {

    private int id;
    private int quantity;
    private String measure;
    private String ingredient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
            ingredient.quantity = Integer.parseInt(jsonObject.getString("quantity"));
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
