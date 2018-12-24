package com.adnd.popularmovies.api;

import com.adnd.popularmovies.models.MovieReview;

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

public class MovieReviewsListConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new MyConverter();
    }

    public class MyConverter implements Converter<ResponseBody, List<MovieReview>> {

        @Override
        public List<MovieReview> convert(ResponseBody value) throws IOException {
            return getListOfMovieReviews(value.string());
        }

        private List<MovieReview> getListOfMovieReviews(String result) {
            List<MovieReview> movieReviews = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    MovieReview movieReview = MovieReview.fromJSONObject(jsonArray.getJSONObject(i));
                    if (movieReview != null) {
                        movieReviews.add(movieReview);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movieReviews;
        }


    }

}
