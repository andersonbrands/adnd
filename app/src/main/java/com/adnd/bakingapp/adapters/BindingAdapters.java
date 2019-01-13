package com.adnd.bakingapp.adapters;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.TextView;

import com.adnd.bakingapp.R;
import com.adnd.bakingapp.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class BindingAdapters {

    @BindingAdapter("android:text")
    public static void setText(TextView textView, List<Ingredient> ingredients) {
        if (ingredients == null || ingredients.size() == 0) {
            return;
        }

        Resources res = textView.getResources();
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
        textView.setText(TextUtils.join("\n", stringList));
    }

}
