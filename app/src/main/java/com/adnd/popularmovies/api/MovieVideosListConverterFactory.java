package com.adnd.popularmovies.api;

import com.adnd.popularmovies.models.MovieVideo;

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

public class MovieVideosListConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new MyConverter();
    }

    public class MyConverter implements Converter<ResponseBody, List<MovieVideo>> {

        @Override
        public List<MovieVideo> convert(ResponseBody value) throws IOException {
            return getListOfMovies(value.string());
        }

        private List<MovieVideo> getListOfMovies(String result) {
            List<MovieVideo> movieVideos = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    MovieVideo movieVideo = MovieVideo.fromJSONObject(jsonArray.getJSONObject(i));
                    if (movieVideo != null) {
                        movieVideos.add(movieVideo);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movieVideos;
        }


    }

}
