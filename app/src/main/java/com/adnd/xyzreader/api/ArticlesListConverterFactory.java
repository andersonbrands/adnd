package com.adnd.xyzreader.api;

import com.adnd.xyzreader.models.Article;

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

public class ArticlesListConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new MyConverter();
    }

    public class MyConverter implements Converter<ResponseBody, List<Article>> {

        @Override
        public List<Article> convert(ResponseBody value) throws IOException {
            return getListOfArticles(value.string());
        }

        private List<Article> getListOfArticles(String result) {
            List<Article> articles = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    Article article = Article.fromJSONObject(jsonArray.getJSONObject(i));
                    if (article != null) {
                        articles.add(article);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return articles;
        }


    }

}
