package com.adnd.bakingapp.api;

import com.adnd.bakingapp.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class RecipesListConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new MyConverter();
    }

    public class MyConverter implements Converter<ResponseBody, List<Recipe>> {

        @Override
        public List<Recipe> convert(ResponseBody value) throws IOException {
            return getListOfRecipes(value.string());
        }

        private List<Recipe> getListOfRecipes(String result) {
            List<Recipe> recipes = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Recipe recipe = Recipe.fromJSONObject(jsonArray.getJSONObject(i));
                    if (recipe != null) {
                        recipes.add(recipe);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return recipes;
        }


    }

}
