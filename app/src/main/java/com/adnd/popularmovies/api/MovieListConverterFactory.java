package com.adnd.popularmovies.api;

import com.adnd.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class MovieListConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new MyConverter();
    }

    public class MyConverter implements Converter<ResponseBody, List<Movie>> {

        @Override
        public List<Movie> convert(ResponseBody value) throws IOException {
            return getListOfMovies(value.string());
        }

        private List<Movie> getListOfMovies(String result) {
            List<Movie> movies = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Movie movie = Movie.fromJSONObject(jsonArray.getJSONObject(i));
                    if (movie != null) {
                        movies.add(movie);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movies;
        }


    }

}
