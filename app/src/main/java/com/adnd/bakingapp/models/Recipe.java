package com.adnd.bakingapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private int servings;
    private String image;

    private List<Ingredient> ingredients;
    private List<Step> steps;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public static Recipe fromJSONString(String jsonString) {
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

    public static Recipe fromJSONObject(JSONObject jsonObject) {
        Recipe recipe = new Recipe();

        try {
            recipe.id = Integer.parseInt(jsonObject.getString("id"));
            recipe.name = jsonObject.getString("name");
            recipe.servings = Integer.parseInt(jsonObject.getString("servings"));
            recipe.image = jsonObject.getString("image");

            JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");
            List<Ingredient> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                Ingredient ingredient = Ingredient.fromJSONObject(ingredientsJsonArray.getJSONObject(i));
                ingredientsList.add(ingredient);
            }
            recipe.ingredients = ingredientsList;

            JSONArray stepsJsonArray = jsonObject.getJSONArray("steps");
            List<Step> stepsList = new ArrayList<>();
            for (int i = 0; i < stepsJsonArray.length(); i++) {
                Step step = Step.fromJSONObject(stepsJsonArray.getJSONObject(i));
                stepsList.add(step);
            }
            recipe.steps = stepsList;

        } catch (JSONException e) {
            e.printStackTrace();
            recipe = null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            recipe = null;
        }

        return recipe;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("name", this.name);
            jsonObject.put("servings", this.servings);
            jsonObject.put("image", this.image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
