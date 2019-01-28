package com.adnd.bakingapp.adapters;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.models.Ingredient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BindingAdapters {

    @BindingAdapter("android:text")
    public static void setText(TextView textView, List<Ingredient> ingredients) {
        if (ingredients == null || ingredients.size() == 0) {
            return;
        }

        Resources res = textView.getResources();

        textView.setText(getIngredientsListText(res, ingredients));
    }

    public static String getIngredientsListText(Resources res, List<Ingredient> ingredients) {
        List<String> stringList = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            String ingredientDetailsLine =
                    String.format(
                            res.getString(R.string.ingredient_details_line),
                            String.valueOf(ingredient.getQuantity()),
                            ingredient.getMeasure(),
                            ingredient.getIngredient());
            stringList.add(ingredientDetailsLine);
        }

        return TextUtils.join("\n", stringList);
    }

    @BindingAdapter(value = {"imgURL", "placeholderRes", "errorRes"})
    public static void setImgURL(ImageView imageView, String imgURL, Drawable placeholderRes, Drawable errorRes) {
        if (TextUtils.isEmpty(imgURL)) {
            imgURL = "Path must not be empty";
        }
        Picasso.get()
                .load(imgURL)
                .placeholder(placeholderRes)
                .error(errorRes)
                .into(imageView);
    }

}
